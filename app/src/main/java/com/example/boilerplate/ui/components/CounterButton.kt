package com.example.boilerplate.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A large circular ± button used to increment or decrement the counter.
 */
@Composable
fun CounterActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.size(72.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Text(
            text = label,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * A standard outlined button used for secondary actions like Reset.
 */
@Composable
fun CounterSecondaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = label)
    }
}
