package com.example.catslistcoroutines.data

import android.util.Log
import kotlinx.coroutines.CancellationException
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
                    val body = response.body()
                    if (body != null) {
                        NetworkResult.Success(body)
                    } else {
                        NetworkResult.Error("Response body is null")
                    }
                } else {
                    NetworkResult.Error("API Error ${response.code()}: ${response.message()}")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("ABCDEF", "getPets catch: $e")
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}
