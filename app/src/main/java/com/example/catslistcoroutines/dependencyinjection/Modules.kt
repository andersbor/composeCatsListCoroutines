package com.example.catslistcoroutines.dependencyinjection

import com.example.catslistcoroutines.data.CatsAPI
import com.example.catslistcoroutines.data.PetsRepository
import com.example.catslistcoroutines.data.PetsRepositoryImpl
import com.example.catslistcoroutines.viewmodel.PetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                //json.asConverterFactory("application/json; charset=UTF8".toMediaType())
                // retrofit converter not working, using Gson
            )
        .baseUrl("https://cataas.com/api/")
        .build()
    }
    single { get<Retrofit>().create(CatsAPI::class.java) }
}
