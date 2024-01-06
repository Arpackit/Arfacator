package com.arpackit.arfacator

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.text.font.FontFamily

import androidx.navigation.compose.rememberNavController

import kotlinx.coroutines.launch

import com.arpackit.arfacator.data.repository.PrefsDataStoreRepository
import com.arpackit.arfacator.ui.theme.MyComposeApplicationTheme
import com.arpackit.arfacator.ui.theme.PoppinsFamily
import com.arpackit.arfacator.ui.theme.DeliusFamily


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val prefsRepo = PrefsDataStoreRepository(this)
        
        setContent {
            val isDark = isSystemInDarkTheme()
            
            var applyDarkTheme by remember { mutableStateOf(isDark) }
            var applyPoppinsFont by remember { mutableStateOf(true) }
            var fontFamily by remember { mutableStateOf<FontFamily>(PoppinsFamily) }
            
            val updateFamily = { fontFamily = if (applyPoppinsFont) PoppinsFamily else DeliusFamily }
            val coScope = rememberCoroutineScope()
            
            coScope.launch {
                applyDarkTheme = prefsRepo.getIsDarkTheme(applyDarkTheme)
                applyPoppinsFont = prefsRepo.getIsPoppinsFont(applyPoppinsFont)
                updateFamily()
            }
            
            MyComposeApplicationTheme(applyDarkTheme, fontFamily) {
                NavGraph(
                    navController = rememberNavController(),
                    isDarkTheme = applyDarkTheme,
                    isPoppinsFont = applyPoppinsFont,
                    onToggleTheme = {
                        applyDarkTheme = !applyDarkTheme
                        coScope.launch {
                            prefsRepo.putIsDarkTheme(applyDarkTheme)
                        }
                    },
                    onToggleFontFamily = {
                        applyPoppinsFont = !applyPoppinsFont
                        updateFamily()
                        coScope.launch {
                            prefsRepo.putIsPoppinsFont(applyPoppinsFont)
                        }
                    })
            }
        }
    }
}