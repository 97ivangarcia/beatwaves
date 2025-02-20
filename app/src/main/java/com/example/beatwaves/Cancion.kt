data class Cancion(
    val id: Int = 0,
    val titulo: String,
    val artista: String,
    val precio: String,
    val imagenResName: String,
    var isLiked: Boolean = false
)