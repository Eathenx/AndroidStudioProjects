package com.example.practica4recycler

import android.net.Uri

//Data class que representa un articulo.
data class Article(
    val title: String,
    val description: String,
    val imageUri: Uri
)
