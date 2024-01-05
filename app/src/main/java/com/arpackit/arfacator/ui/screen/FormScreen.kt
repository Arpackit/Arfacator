package com.arpackit.arfacator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.arpackit.arfacator.R
import com.arpackit.arfacator.data.Account
import com.arpackit.arfacator.ui.component.FormTextField
import com.arpackit.arfacator.ui.component.TopBar
import com.arpackit.arfacator.ui.viewmodel.FormViewModel
import com.arpackit.arfacator.util.showToast


private lateinit var vm: FormViewModel


@Composable
fun FormScreen(
    curAccId: Int?, // Id of the to be editing account
    onNavBack: () -> Unit
) {
    vm = viewModel()
    
    Column(Modifier
        .fillMaxSize()
        .background(colorScheme.background)
    ) {
    
        var nameValue by remember { mutableStateOf("") }
        var keyValue by remember { mutableStateOf("") }
        var descValue by remember { mutableStateOf("") }
        var account by remember { mutableStateOf<Account>(
            Account(name = "", key = "", description = "")
        )}
        
        LaunchedEffect(Unit) {
            if (curAccId != -1) {
                // read from db
                account = vm.find(curAccId!! - 1)
                account.run {
                    nameValue = name
                    keyValue = key
                    descValue = description
                }
            }
        }
        
        FormScreenTopBar(onNavBack = onNavBack) {
            nameValue = nameValue.trim()
            keyValue = keyValue.trim()
            descValue = descValue.trim()
            
            when {
                nameValue.isBlank() && keyValue.isBlank() -> showToast(ctx, "Name and secret key are mandatory!")
                nameValue.isBlank() -> showToast(ctx, "Name is mandatory!")
                keyValue.isBlank() -> showToast(ctx, "Your secret key is mandatory!")
                else -> {
                    account.apply {
                        name = nameValue
                        key = keyValue
                        description = descValue
                    }
                    
                    // insert to db
                    vm.add(account)
                    
                    onNavBack()
                }
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
        
            FormTextField(
                Outlined.Person,
                R.string.name,
                R.string.name_placeholder,
                nameValue,
            ) { nameValue = it }
                        
            FormTextField(
                Outlined.Lock,
                R.string.key,
                R.string.key_placeholder,
                keyValue,
                pastable = true,
                onPaste = {
                    clipboardManager.getText()
                        ?.text
                        ?.also { keyValue = it }
                }
            ) { keyValue = it }
                        
            FormTextField(
                Outlined.Info,
                R.string.desc,
                R.string.desc_placeholder,
                descValue,
                optional = true
            ) { descValue = it }
            
        }
        
    }
}


@Composable
fun FormScreenTopBar(
    onNavBack: () -> Unit,
    onSave: () -> Unit,
) {
    TopBar(
        title = stringResource(R.string.add_account),
        backable = true,
        onBack = onNavBack,
    ) {
        IconButton(onClick = onSave) {
            Icon(
                Outlined.Check,
                stringResource(R.string.save),
                tint = colorScheme.onPrimary)
        }
    }
}