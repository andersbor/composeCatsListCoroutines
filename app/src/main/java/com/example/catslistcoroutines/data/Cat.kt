package com.example.catslistcoroutines.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// the json coming from Cat API has changed since the book was published
// https://github.com/PacktPublishing/Mastering-Kotlin-for-Android/blob/main/chaptersix/app/src/main/java/com/packt/chaptersix/data/Cat.kt

@Serializable
data class Cat(
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("id")
    val id: String,
    //@SerialName("owner")
    //val owner: String = "",
    @SerialName("tags")
    val tags: List<String>,
    //@SerialName("updatedAt")
    //val updatedAt: String = ""
)
