package com.arpackit.arfacator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.*

import androidx.compose.material3.MaterialTheme.colorScheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.arpackit.arfacator.R
import com.arpackit.arfacator.ui.component.TopBar
import com.arpackit.arfacator.ui.component.Preference
import com.arpackit.arfacator.ui.icon.Contrast
import com.arpackit.arfacator.ui.icon.TypeSpecimen
import com.arpackit.arfacator.ui.icon.Language
import com.arpackit.arfacator.util.showToast


@Composable
fun PreferencesScreen(
    isDarkTheme: Boolean,
    isPoppinsFont: Boolean,
    onToggleTheme: () -> Unit,
    onToggleFontFamily: () -> Unit,
    onNavBack: () -> Unit
) {
    Column(Modifier
        .fillMaxSize()
        .background(colorScheme.background)
    ) {
        
        TopBar(
            title = stringResource(R.string.preferences),
            backable = true,
            onBack = onNavBack)
            
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            
            Preference(
                icon = Contrast(),
                title = stringResource(R.string.app_theme),
                subTitle = stringResource(R.string.tap_to_apply_theme, 
                    stringResource(
                        if (isDarkTheme) R.string.theme_light
                        else R.string.theme_dark
                    )),
                onClick = onToggleTheme)
                
            Preference(
                icon = TypeSpecimen(),
                title = stringResource(R.string.font_family),
                subTitle = stringResource(R.string.tap_to_apply_font, 
                    stringResource(
                        if (isPoppinsFont) R.string.family_delius 
                        else R.string.family_poppins
                    )),
                onClick = onToggleFontFamily)
                
            val coming_soon = stringResource(R.string.coming_soon)
            
            Preference(
                icon = Language(),
                title = stringResource(R.string.display_lang),
                subTitle = stringResource(R.string.tap_to_select_lang),
            ) {
                showToast(ctx, coming_soon)
            }
            
        }
        
    }
    
}