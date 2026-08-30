package com.example.boilerplate.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Holds and manages the counter state.
 * Survives configuration changes (e.g., screen rotation) because it extends ViewModel.
 */
class CounterViewModel : ViewModel() {

    // Backing mutable state — only writable inside this ViewModel
    private val _count = MutableStateFlow(0)

    // Exposed as a read-only StateFlow to the UI layer
    val count: StateFlow<Int> = _count.asStateFlow()

    /** Increment the counter by 1. */
    fun increment() {
        _count.value++
    }

    /** Decrement the counter by 1. */
    fun decrement() {
        _count.value--
    }

    /** Reset the counter back to 0. */
    fun reset() {
        _count.value = 0
    }
}
