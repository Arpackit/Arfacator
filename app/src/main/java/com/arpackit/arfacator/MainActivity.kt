package com.arpackit.arfacator

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.text.font.FontFamily

import androidx.navigation.compose.rememberNavController

import com.arpackit.arfacator.ui.theme.MyComposeApplicationTheme
import com.arpackit.arfacator.ui.theme.PoppinsFamily
import com.arpackit.arfacator.ui.theme.DeliusFamily


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val darkTheme = isSystemInDarkTheme()
            var applyDarkTheme by remember { mutableStateOf(darkTheme) }
            var fontFamily by remember { mutableStateOf<FontFamily>(PoppinsFamily) }
            
            MyComposeApplicationTheme(applyDarkTheme, fontFamily) {
                NavGraph(
                    navController = rememberNavController(),
                    isDarkTheme = applyDarkTheme,
                    isPoppinsFont = fontFamily == PoppinsFamily,
                    onToggleTheme = {
                        applyDarkTheme = !applyDarkTheme
                    },
                    onToggleFontFamily = {
                        fontFamily = if (fontFamily == PoppinsFamily) DeliusFamily else PoppinsFamily
                    })
            }
        }
    }
}