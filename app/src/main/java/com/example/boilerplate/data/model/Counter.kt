package com.example.boilerplate.data.model

/**
 * Data class representing the counter entity/state in the data layer.
 *
 * @property value The current numeric value of the counter.
 * @property lastModified Timestamp of when the counter was last modified.
 */
data class Counter(
    val value: Int = 0,
    val lastModified: Long = System.currentTimeMillis()
)
