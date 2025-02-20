package com.example.beatwaves

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class likes : AppCompatActivity() {

    private lateinit var adapter: CancionAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener userId desde SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userId = prefs.getInt("userId", -1)

        // Verificar autenticación
        if (userId == -1) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, iniciarsesion::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_likes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CancionAdapter(
            userId = userId,
            databaseHelper = dbHelper,
            onFavoriteUpdated = { actualizarFavoritos() }
        )
        recyclerView.adapter = adapter

        actualizarFavoritos()
        configurarBotones()
    }

    private fun actualizarFavoritos() {
        val favoritos = dbHelper.getFavoritos(userId)
        adapter.updateData(favoritos)
    }

    private fun configurarBotones() {
        findViewById<ImageButton>(R.id.likesGoHome).setOnClickListener {
            startActivity(Intent(this, catalogogeneros::class.java))
        }
        findViewById<ImageButton>(R.id.likesGoSubir).setOnClickListener {
            startActivity(Intent(this, subircancion::class.java))
        }
        findViewById<ImageButton>(R.id.likesGoCompra).setOnClickListener {
            startActivity(Intent(this, carrito::class.java))
        }
        findViewById<ImageButton>(R.id.likesGoPerfil).setOnClickListener {
            startActivity(Intent(this, perfiluser::class.java))
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
