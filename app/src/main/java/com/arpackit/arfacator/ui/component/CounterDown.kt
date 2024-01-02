package com.arpackit.arfacator.ui.component;

import androidx.compose.foundation.layout.size

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.launch

import com.arpackit.arfacator.util.scheduleAtFixedRate


@Composable
fun CounterDown(
    duration: Float,
    elapsed: Float,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onTimeOver: () -> Unit
) {
    var progress by remember { mutableStateOf(1f - elapsed / duration) }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        scope.launch {
            scheduleAtFixedRate(0, 1) {
                if (progress <= 0) {
                    progress = 1f
                    onTimeOver()
                }
                progress -= 1f / duration
            }
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