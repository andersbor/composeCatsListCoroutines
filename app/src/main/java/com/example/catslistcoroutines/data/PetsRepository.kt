package com.example.catslistcoroutines.data

interface PetsRepository {
    suspend fun getPets(): NetworkResult<List<Cat>>
}