package com.arpackit.arfacator.ui.component;

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row

import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.arpackit.arfacator.ui.theme.*
import com.arpackit.arfacator.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotpSwipableCard(
    name: String,
    totp: String,
    description: String,
    duration: Float = 30f,
    onTotpExpire: () -> Unit,
    onSwipedToStart: () -> Unit,
    onSwipedToEnd: () -> Unit,
    onClick: () -> Unit,
) {
    var isTotpCopied by remember { mutableStateOf(false) }
            
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = appRoundedShape,
        elevation = CardDefaults.cardElevation(1.dp),
        onClick = {
            onClick()
            isTotpCopied = true
            schedule(1) { isTotpCopied = false }
        },
    ) {
    
        val dismissState = rememberDismissState()
        
        with(dismissState) {
            if (isDismissed(DismissDirection.EndToStart)) {
                onSwipedToStart()
                LaunchedEffect(Unit) { reset() }
            } else if (isDismissed(DismissDirection.StartToEnd)) {
                onSwipedToEnd()
                LaunchedEffect(Unit) { reset() }
            }
        }
    
        SwipeToDismiss(
            modifier = Modifier.clip(appRoundedShape),
            state = dismissState,
            directions = setOf(
                DismissDirection.StartToEnd,
                DismissDirection.EndToStart,
            ),
            background = {
                val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.8f)
                        DismissValue.DismissedToEnd -> colorScheme.secondary.copy(alpha = 0.8f)
                        DismissValue.Default -> colorScheme.background
                    }
                )
                    
                val icon = when (direction) {
                    DismissDirection.StartToEnd -> Outlined.Edit
                    DismissDirection.EndToStart -> Outlined.Delete
                }
                    
                val scale by animateFloatAsState(
                    if (dismissState.targetValue == DismissValue.Default) 0f
                    else 1.2f
                )
                    
                val alignment = when (direction) {
                    DismissDirection.StartToEnd -> Alignment.CenterStart
                    DismissDirection.EndToStart -> Alignment.CenterEnd
                }
                    
                Box(Modifier.fillMaxSize().background(color).padding(horizontal = 16.dp)) {
                    Icon(icon, "", Modifier.scale(scale).align(alignment), WHITE)
                }
            },
            dismissContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.surface)
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    CardText(name, typography.titleSmall)
            
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CardText(
                            totp,
                            typography.headlineMedium,
                            if (isTotpCopied) colorScheme.secondary else colorScheme.primary)
                        
                        CounterDown(duration, colorScheme.primary, onTotpExpire)
                    }
            
                    if (description.isNotBlank())
                        CardText(description, typography.bodySmall, maxLines = 2)
                }
            }
        )
        
    }
}


@Composable
fun CardText(
    text: String, 
    style: TextStyle = TextStyle(),
    color: Color = colorScheme.onBackground,
    maxLines: Int = 1
) {
    Text(
        text = text,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}