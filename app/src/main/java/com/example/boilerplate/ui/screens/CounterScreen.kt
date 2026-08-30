package com.example.boilerplate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boilerplate.theme.BoilerplateTheme
import com.example.boilerplate.ui.components.CounterActionButton
import com.example.boilerplate.ui.components.CounterSecondaryButton
import com.example.boilerplate.viewmodel.CounterViewModel

/**
 * Main counter screen. Reads state from [CounterViewModel] and delegates
 * all user actions back to it — the composable itself has no business logic.
 */
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel()
) {
    val count by viewModel.count.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            CounterContent(
                count = count,
                onIncrement = viewModel::increment,
                onDecrement = viewModel::decrement,
                onReset = viewModel::reset
            )
        }
    }
}

/** Stateless inner layout — easy to test and preview in isolation. */
@Composable
private fun CounterContent(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App title
        Text(
            text = "Kotlin Boilerplate",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Counter display
        Text(
            text = count.toString(),
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.semantics {
                contentDescription = "Counter value: $count"
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ± action buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CounterActionButton(label = "−", onClick = onDecrement)
            CounterActionButton(label = "+", onClick = onIncrement)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reset button
        CounterSecondaryButton(
            label = "Reset",
            onClick = onReset,
            modifier = Modifier.width(160.dp)
        )
    }
}

@Preview(showBackground = true, name = "Counter – Light")
@Composable
private fun CounterScreenPreviewLight() {
    BoilerplateTheme(darkTheme = false) {
        CounterContent(count = 42, onIncrement = {}, onDecrement = {}, onReset = {})
    }
}

@Preview(showBackground = true, name = "Counter – Dark")
@Composable
private fun CounterScreenPreviewDark() {
    BoilerplateTheme(darkTheme = true) {
        CounterContent(count = 42, onIncrement = {}, onDecrement = {}, onReset = {})
    }
}
