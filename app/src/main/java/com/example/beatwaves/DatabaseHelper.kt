package com.example.beatwaves

import Cancion
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "BeatWaves.db"
        private const val DATABASE_VERSION = 4

        // Tablas
        const val TABLE_USERS = "usuarios"
        const val TABLE_CANCIONES = "canciones"
        const val TABLE_FAVORITOS = "favoritos"

        // Columnas comunes
        const val COLUMN_ID = "id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_CANCION_ID = "cancion_id"

        // Usuarios
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // Canciones
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_ARTISTA = "artista"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_IMAGEN = "imagen"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de usuarios
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        // Crear tabla de canciones
        val createCancionesTable = """
            CREATE TABLE $TABLE_CANCIONES (
                $COLUMN_CANCION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT NOT NULL,
                $COLUMN_ARTISTA TEXT NOT NULL,
                $COLUMN_PRECIO TEXT NOT NULL,
                $COLUMN_IMAGEN TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createCancionesTable)

        // Crear tabla de favoritos
        val createFavoritosTable = """
            CREATE TABLE $TABLE_FAVORITOS (
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_CANCION_ID INTEGER NOT NULL,
                PRIMARY KEY ($COLUMN_USER_ID, $COLUMN_CANCION_ID),
                FOREIGN KEY ($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID),
                FOREIGN KEY ($COLUMN_CANCION_ID) REFERENCES $TABLE_CANCIONES($COLUMN_CANCION_ID)
            )
        """.trimIndent()
        db.execSQL(createFavoritosTable)

        // Insertar datos iniciales
        insertarCancionesEjemplo(db)
        insertarUsuarioAdmin(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 4) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITOS")
            onCreate(db)
        }
    }

    // Usuarios
    fun insertUser(email: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email.trim().lowercase())
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun getUserByEmail(email: String): Cursor {
        val db = readableDatabase
        return db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_EMAIL, COLUMN_PASSWORD),
            "$COLUMN_EMAIL = ?",
            arrayOf(email.trim().lowercase()),
            null, null, null
        )
    }

    fun validateUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = """
        SELECT COUNT(*) 
        FROM $TABLE_USERS 
        WHERE $COLUMN_EMAIL = ? 
        AND $COLUMN_PASSWORD = ?
    """.trimIndent()

        return db.rawQuery(query, arrayOf(email, password)).use { cursor ->
            cursor.moveToFirst() && cursor.getInt(0) > 0
        }
    }

    // Canciones
    fun insertCancion(titulo: String, artista: String, precio: String, imagen: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, titulo)
            put(COLUMN_ARTISTA, artista)
            put(COLUMN_PRECIO, precio)
            put(COLUMN_IMAGEN, imagen)
        }
        return db.insert(TABLE_CANCIONES, null, values)
    }

    fun getAllCanciones(userId: Int): List<Cancion> {
        val db = readableDatabase
        val query = """
            SELECT c.*, 
            CASE WHEN f.$COLUMN_USER_ID IS NOT NULL THEN 1 ELSE 0 END AS is_favorita
            FROM $TABLE_CANCIONES c
            LEFT JOIN $TABLE_FAVORITOS f 
            ON c.$COLUMN_CANCION_ID = f.$COLUMN_CANCION_ID 
            AND f.$COLUMN_USER_ID = ?
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val canciones = mutableListOf<Cancion>()
        while (cursor.moveToNext()) {
            canciones.add(
                Cancion(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANCION_ID)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    artista = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTISTA)),
                    precio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRECIO)),
                    imagenResName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                    isLiked = cursor.getInt(cursor.getColumnIndexOrThrow("is_favorita")) == 1
                )
            )
        }
        cursor.close()
        return canciones
    }

    // Favoritos
    fun toggleFavorito(userId: Int, cancionId: Int): Boolean {
        return if (isCancionFavorita(userId, cancionId)) {
            removeFavorito(userId, cancionId)
        } else {
            addFavorito(userId, cancionId)
        }
    }

    private fun addFavorito(userId: Int, cancionId: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_CANCION_ID, cancionId)
        }
        return db.insert(TABLE_FAVORITOS, null, values) != -1L
    }

    fun removeFavorito(userId: Int, cancionId: Int): Boolean {
        val db = writableDatabase
        return db.delete(
            TABLE_FAVORITOS,
            "$COLUMN_USER_ID = ? AND $COLUMN_CANCION_ID = ?",
            arrayOf(userId.toString(), cancionId.toString())
        ) > 0
    }

    fun getFavoritos(userId: Int): List<Cancion> {
        val db = readableDatabase
        val query = """
            SELECT c.* 
            FROM $TABLE_CANCIONES c
            INNER JOIN $TABLE_FAVORITOS f 
            ON c.$COLUMN_CANCION_ID = f.$COLUMN_CANCION_ID
            WHERE f.$COLUMN_USER_ID = ?
        """.trimIndent()
        return db.rawQuery(query, arrayOf(userId.toString())).use { cursor ->
            val canciones = mutableListOf<Cancion>()
            while (cursor.moveToNext()) {
                canciones.add(
                    Cancion(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANCION_ID)),
                        titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                        artista = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTISTA)),
                        precio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRECIO)),
                        imagenResName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                        isLiked = true
                    )
                )
            }
            canciones
        }
    }

    fun isCancionFavorita(userId: Int, cancionId: Int): Boolean {
        val db = readableDatabase
        val query = """
            SELECT COUNT(*) 
            FROM $TABLE_FAVORITOS 
            WHERE $COLUMN_USER_ID = ? 
            AND $COLUMN_CANCION_ID = ?
        """.trimIndent()
        db.rawQuery(query, arrayOf(userId.toString(), cancionId.toString())).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getInt(0) > 0 else false
        }
    }

    // Datos iniciales
    private fun insertarCancionesEjemplo(db: SQLiteDatabase) {
        val canciones = listOf(
            Cancion(titulo = "Bangarang", artista = "Skrillex", precio = "2€", imagenResName = "bangarang"),
            Cancion(titulo = "First of the Year", artista = "Skrillex", precio = "1.5€", imagenResName = "firstoftheyear"),
            Cancion(titulo = "Scary Monsters", artista = "Skrillex", precio = "2€", imagenResName = "scarymonsters")
        )
        canciones.forEach { cancion ->
            val values = ContentValues().apply {
                put(COLUMN_TITULO, cancion.titulo)
                put(COLUMN_ARTISTA, cancion.artista)
                put(COLUMN_PRECIO, cancion.precio)
                put(COLUMN_IMAGEN, cancion.imagenResName)
            }
            db.insert(TABLE_CANCIONES, null, values)
        }
    }

    private fun insertarUsuarioAdmin(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, "admin@beatwaves.com".trim().lowercase())
            put(COLUMN_PASSWORD, "Admin1234")
        }
        db.insert(TABLE_USERS, null, values)
    }
}
