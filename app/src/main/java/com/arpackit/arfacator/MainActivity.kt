package com.arpackit.arfacator

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.lifecycle.lifecycleScope

import androidx.navigation.compose.rememberNavController

import kotlinx.coroutines.launch

import com.arpackit.arfacator.data.repository.PrefsDataStoreRepository
import com.arpackit.arfacator.ui.theme.MyComposeApplicationTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val prefsRepo = PrefsDataStoreRepository(this)
        
        setContent {
            val isDark = isSystemInDarkTheme()
            
            var applyDarkTheme by remember { mutableStateOf(isDark) }
            var fontId by remember { mutableStateOf(0) }
            
            lifecycleScope.launch {
                applyDarkTheme = prefsRepo.getIsDarkTheme(applyDarkTheme)
                fontId = prefsRepo.getFontId(fontId)
            }
            
            MyComposeApplicationTheme(applyDarkTheme, fontId) {
                NavGraph(
                    navController = rememberNavController(),
                    isDarkTheme = applyDarkTheme,
                    fontId = fontId,
                    onToggleTheme = {
                        applyDarkTheme = !applyDarkTheme
                        lifecycleScope.launch {
                            prefsRepo.putIsDarkTheme(applyDarkTheme)
                        }
                    },
                    onToggleFontFamily = {
                        fontId = if (fontId == 0) 1 else 0
                        lifecycleScope.launch {
                            prefsRepo.putFontId(fontId)
                        }
                    })
            }
        }
    }
}