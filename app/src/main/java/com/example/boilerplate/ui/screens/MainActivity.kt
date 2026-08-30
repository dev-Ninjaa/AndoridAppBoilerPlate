package com.example.boilerplate.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.boilerplate.theme.BoilerplateTheme

/**
 * Single-activity entry point.
 * Edge-to-edge display is enabled so the app draws behind the system bars.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Let the app draw edge-to-edge behind system bars
        enableEdgeToEdge()

        setContent {
            BoilerplateTheme {
                CounterScreen()
            }
        }
    }
}
