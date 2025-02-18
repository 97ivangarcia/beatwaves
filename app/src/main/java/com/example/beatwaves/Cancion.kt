package com.example.beatwaves

data class Cancion(
    val titulo: String,
    val artista: String,
    val precio: String,
    val imagenResName: String,
    var isLiked: Boolean = false
)
