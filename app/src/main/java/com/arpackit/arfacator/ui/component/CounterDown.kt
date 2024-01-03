package com.arpackit.arfacator.ui.component;

import androidx.compose.foundation.layout.size

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import java.util.Date

import com.arpackit.arfacator.util.scheduleAtFixedRate


@Composable
fun CounterDown(
    duration: Float,
    color: Color = colorScheme.onBackground,
    onTimeOver: () -> Unit
) {
    val calcProgress = { (duration - Date().seconds % duration) / duration }
    var progress by remember { mutableStateOf(calcProgress()) }
    
    LaunchedEffect(Unit) {
        scheduleAtFixedRate(0, 1) {
            progress = calcProgress()
            if (progress <= 0) onTimeOver()
        }
    }
    
    CircularProgressIndicator(
        progress = progress,
        modifier = Modifier.size(size = 20.dp),  
        color = color,
        trackColor = color.copy(alpha = .2f),
        strokeWidth = 10.dp,
    )
}