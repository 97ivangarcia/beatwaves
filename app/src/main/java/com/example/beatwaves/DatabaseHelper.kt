package com.example.beatwaves

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "BeatWaves.db"
        private const val DATABASE_VERSION = 3 // Incremento para manejar cambios en la BD

        // Tabla de usuarios
        const val TABLE_USERS = "usuarios"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // Tabla de canciones
        const val TABLE_CANCIONES = "canciones"
        const val COLUMN_CANCION_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_ARTISTA = "artista"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_IMAGEN = "imagen" // Nombre de la imagen en drawable
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de usuarios (sin cambios)
        val createUsersTable = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EMAIL TEXT NOT NULL, " +
                "$COLUMN_PASSWORD TEXT NOT NULL)"
        db.execSQL(createUsersTable)

        // Crear tabla de canciones (nueva)
        val createCancionesTable = "CREATE TABLE $TABLE_CANCIONES (" +
                "$COLUMN_CANCION_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITULO TEXT NOT NULL, " +
                "$COLUMN_ARTISTA TEXT NOT NULL, " +
                "$COLUMN_PRECIO TEXT NOT NULL, " +
                "$COLUMN_IMAGEN TEXT NOT NULL)"
        db.execSQL(createCancionesTable)

        // Insertar canciones de prueba
        insertarCancionesEjemplo(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) { // Si la versión es anterior a 3, solo creamos la tabla de canciones
            val createCancionesTable = "CREATE TABLE IF NOT EXISTS $TABLE_CANCIONES (" +
                    "$COLUMN_CANCION_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITULO TEXT NOT NULL, " +
                    "$COLUMN_ARTISTA TEXT NOT NULL, " +
                    "$COLUMN_PRECIO TEXT NOT NULL, " +
                    "$COLUMN_IMAGEN TEXT NOT NULL)"
            db.execSQL(createCancionesTable)
        }
    }

    // ✅ Función para insertar canciones en la base de datos
    fun insertCancion(titulo: String, artista: String, precio: String, imagen: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, titulo)
            put(COLUMN_ARTISTA, artista)
            put(COLUMN_PRECIO, precio)
            put(COLUMN_IMAGEN, imagen) // Guardamos el nombre de la imagen
        }
        return db.insert(TABLE_CANCIONES, null, values)
    }

    // ✅ Función para obtener todas las canciones
    fun getAllCanciones(): List<Cancion> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CANCIONES", null)

        val canciones = mutableListOf<Cancion>()
        while (cursor.moveToNext()) {
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
            val artista = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTISTA))
            val precio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRECIO))
            val imagenResName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN))

            val resId = obtenerIdImagen(imagenResName)
            canciones.add(Cancion(titulo, artista, precio, imagenResName))
        }
        cursor.close()
        return canciones
    }

    // ✅ Función para insertar canciones de ejemplo en la BD
    private fun insertarCancionesEjemplo(db: SQLiteDatabase) {
        val canciones = listOf(
            Cancion("Bangarang", "Skrillex", "2€", "bangarang"),
            Cancion("First of the Year (Equinox)", "Skrillex", "1.5€", "firstoftheyear"),
            Cancion("Scary Monsters and Nice Sprites", "Skrillex", "2€", "scarymonsters"),
            Cancion("Kill Everybody", "Skrillex", "1.2€", "killeverybody"),
            Cancion("Ragga Bomb", "Skrillex", "1.8€", "ragga")
        )

        for (cancion in canciones) {
            val values = ContentValues().apply {
                put(COLUMN_TITULO, cancion.titulo)
                put(COLUMN_ARTISTA, cancion.artista)
                put(COLUMN_PRECIO, cancion.precio)
                put(COLUMN_IMAGEN, cancion.imagenResName) // Nombre de la imagen en drawable
            }
            db.insert(TABLE_CANCIONES, null, values)
        }
    }

    // ✅ Función para obtener el recurso de imagen desde su nombre en drawable
    private fun obtenerIdImagen(nombreImagen: String): Int {
        return when (nombreImagen) {
            "bangarang" -> R.drawable.bangarang
            "firstoftheyear" -> R.drawable.firstoftheyear
            "scarymonsters" -> R.drawable.scarymonsters
            "killeverybody" -> R.drawable.killeverybody
            "ragga" -> R.drawable.ragga
            else -> R.drawable.heartpngfull // Imagen por defecto si no encuentra la imagen
        }
    }

    // ✅ Mantengo TODAS tus funciones de usuarios sin cambios
    fun insertUser(email: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password) // Importante cifrar contraseñas en producción
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun getAllUsers(): List<Pair<String, String>> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_EMAIL, COLUMN_PASSWORD),
            null, null, null, null, null
        )

        val users = mutableListOf<Pair<String, String>>()
        while (cursor.moveToNext()) {
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            users.add(Pair(email, password))
        }
        cursor.close()
        return users
    }

    fun validateUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
}
