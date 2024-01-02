package com.arpackit.arfacator.ui.screen

import android.content.Context

import androidx.compose.foundation.background
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items

import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.*

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.arpackit.arfacator.R
import com.arpackit.arfacator.data.Account
import com.arpackit.arfacator.ui.component.*
import com.arpackit.arfacator.ui.icon.*
import com.arpackit.arfacator.ui.viewmodel.MainViewModel
import com.arpackit.arfacator.util.*


lateinit var ctx: Context
lateinit var clipboardManager: ClipboardManager
lateinit var COPIED_STRING_RES: String

private lateinit var vm: MainViewModel


@Composable
fun MainScreen(
    onAddAccount: () -> Unit,
    onEditAccount: (Int) -> Unit,
    onNavToPreferencesScreen: () -> Unit,
    onNavToAboutScreen: () -> Unit,
) {
    ctx = LocalContext.current
    clipboardManager = LocalClipboardManager.current
    COPIED_STRING_RES = stringResource(R.string.copied)
    
    vm = viewModel()
    
    Box(Modifier
        .fillMaxSize()
        .background(colorScheme.background)
    ) {
        
        Column(Modifier.fillMaxSize()) {
            var isLoading by remember { mutableStateOf(true) }
            var viewAsGrid by remember { mutableStateOf(true) }
            var isEmpty = vm.accounts.isEmpty()
                        
            schedule(1) { isLoading = false }
            
            MainScreenTopBar(
                onNavToPreferencesScreen = onNavToPreferencesScreen,
                onNavToAboutScreen = onNavToAboutScreen,
                onSwitchView = { viewAsGrid = it },
            )
            
            when {
                isLoading || isEmpty -> LoadingOrEmpty(isLoading)
                else -> AccountsGrid(viewAsGrid, Modifier.weight(1f), vm.accounts, onEditAccount)
            }
        }
        
        FAB(Modifier.align(Alignment.BottomEnd), onAddAccount)
    }
    
}


@Composable
fun MainScreenTopBar(
    onNavToPreferencesScreen: () -> Unit,
    onNavToAboutScreen: () -> Unit,
    onSwitchView: (Boolean) -> Unit,
    onSearch: () -> Unit = {},
) {
    TopBar {
        var isMenuExpanded by remember { mutableStateOf(false) }
        var viewAsGrid by remember { mutableStateOf(true) }
    
        IconButton(onClick = onSearch) {
            Icon(
                Outlined.Search,
                stringResource(R.string.search),
                tint = colorScheme.onPrimary)
        }
        
        IconButton(onClick = {
            viewAsGrid = !viewAsGrid
            onSwitchView(viewAsGrid)
        }) {
            Icon(
                if (viewAsGrid) ViewAgenda() else GridView(),
                stringResource(R.string.switch_view),
                tint = colorScheme.onPrimary)
        }
        
        IconButton(onClick = { isMenuExpanded = true }) {
            Icon(
                Outlined.MoreVert,
                stringResource(R.string.more),
                tint = colorScheme.onPrimary)
        }
    
        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(stringResource(R.string.preferences))
                },
                leadingIcon = {
                    Icon(Outlined.Settings, stringResource(R.string.preferences))
                },
                onClick = onNavToPreferencesScreen)
                
            DropdownMenuItem(
                text = {
                    Text(stringResource(R.string.about))
                },
                leadingIcon = {
                    Icon(Outlined.Info, stringResource(R.string.about))
                },
                onClick = onNavToAboutScreen)
        }
    }
}


@Composable
fun LoadingOrEmpty(isLoading: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isLoading) CircularProgressIndicator(
            modifier = Modifier.size(size = 48.dp),  
            color = colorScheme.primary,
            strokeWidth = 4.dp,
        )
        else Text(
            text = stringResource(R.string.empty_list),
            color = colorScheme.onBackground,
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsGrid(
    viewAsGrid: Boolean,
    modifier: Modifier,
    accounts: List<Account>,
    onEditAccount: (Int) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = 
            if (viewAsGrid) StaggeredGridCells.Adaptive(minSize = 155.dp) 
            else StaggeredGridCells.Fixed(1),
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 88.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        items(
            items = accounts,
            key = { it.id }
        ) { account ->
            var confirmBeforeDeletion by remember { mutableStateOf(false) }
        
            if (confirmBeforeDeletion) {
                ConfirmationDialog(
                    title = stringResource(R.string.dialog_delete_title, account.name),
                    text = stringResource(R.string.dialog_delete_text),
                    confirmText = stringResource(R.string.delete),
                    dismissText = stringResource(R.string.keep),
                    onDismiss = { confirmBeforeDeletion = false }
                ) {
                    // Delete account
                }
            }
           
            var totp by remember { mutableStateOf("") }
            totp = generateTotp(account.key)
            
            TotpSwipableCard(
                name = account.name,
                totp = totp,
                description = account.description,
                onTotpExpire = { totp = generateTotp(account.key) },
                onSwipedToStart = {
                    confirmBeforeDeletion = true
                },
                onSwipedToEnd = {
                    onEditAccount(account.id)
                },
                onClick = {
                    copyToClipboard(totp)
                },
            )
        }
    }
}


fun copyToClipboard(text: String) {
    clipboardManager.setText(AnnotatedString(text))
    showToast(ctx, COPIED_STRING_RES)
}