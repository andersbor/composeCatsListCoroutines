package com.example.catslistcoroutines

import com.example.catslistcoroutines.data.Cat

data class PetsUIState(
    val isLoading: Boolean = false,
    val pets: List<Cat> = emptyList(),
    val error: String? = null
)