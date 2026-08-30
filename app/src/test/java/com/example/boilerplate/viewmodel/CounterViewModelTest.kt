package com.example.boilerplate.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CounterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CounterViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CounterViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial counter value is zero`() = runTest {
        assertEquals(0, viewModel.count.value)
    }

    @Test
    fun `increment increases counter by one`() = runTest {
        viewModel.increment()
        assertEquals(1, viewModel.count.value)
    }

    @Test
    fun `decrement decreases counter by one`() = runTest {
        viewModel.decrement()
        assertEquals(-1, viewModel.count.value)
    }

    @Test
    fun `reset returns counter to zero`() = runTest {
        viewModel.increment()
        viewModel.increment()
        viewModel.reset()
        assertEquals(0, viewModel.count.value)
    }

    @Test
    fun `multiple increments accumulate correctly`() = runTest {
        repeat(5) { viewModel.increment() }
        assertEquals(5, viewModel.count.value)
    }
}
