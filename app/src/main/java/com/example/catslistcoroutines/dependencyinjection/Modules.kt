package com.example.catslistcoroutines.dependencyinjection

import com.example.catslistcoroutines.data.CatsAPI
import com.example.catslistcoroutines.data.PetsRepository
import com.example.catslistcoroutines.data.PetsRepositoryImpl
import com.example.catslistcoroutines.viewmodel.PetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// jakewarton is now official retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module {
    single<PetsRepository> { PetsRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { PetsViewModel(get()) }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
                //Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
        .baseUrl("https://cataas.com/api/")
        .build()
    }
    single { get<Retrofit>().create(CatsAPI::class.java) }
}
