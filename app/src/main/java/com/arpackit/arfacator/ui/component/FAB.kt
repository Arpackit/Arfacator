package com.arpackit.arfacator.ui.component

import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Add

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun FAB(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        onClick = onClick,
        shape = appRoundedShape,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Icon(Rounded.Add, "")
    }
}