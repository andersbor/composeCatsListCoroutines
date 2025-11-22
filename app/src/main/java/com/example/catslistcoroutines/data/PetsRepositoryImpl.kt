package com.example.catslistcoroutines.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PetsRepositoryImpl(
    private val catsAPI: CatsAPI,
    private val dispatcher: CoroutineDispatcher
) : PetsRepository {
    override suspend fun getPets(): NetworkResult<List<Cat>> {
        Log.d("ABCDEF", "getPets: start")
        return withContext(dispatcher) {
            try {
                val response = catsAPI.getCats("cute")
                Log.d("ABCDEF", "getPets: $response")
                if (response.isSuccessful) {
                    // TODO check on !!
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                Log.e("ABCDEF", "getPets catch: $e")
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}
