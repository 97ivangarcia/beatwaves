package com.example.beatwaves

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class comprarcanciones : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: CancionAdapter
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
        setContentView(R.layout.activity_comprarcanciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCanciones)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = CancionAdapter(
            userId = userId,
            databaseHelper = dbHelper,
            onFavoriteUpdated = { actualizarListaCanciones() }
        )
        recycler.adapter = adapter

        actualizarListaCanciones()
        configurarBotones()
    }

    private fun actualizarListaCanciones() {
        val canciones = dbHelper.getAllCanciones(userId)
        adapter.updateData(canciones)
    }

    private fun configurarBotones() {
        val volvercatalogo = findViewById<Button>(R.id.botonvolver)
        val anadircarrito = findViewById<Button>(R.id.anadircarrito)
        val comprar = findViewById<Button>(R.id.comprar)

        volvercatalogo.setOnClickListener {
            startActivity(Intent(this@comprarcanciones, catalogogeneros::class.java))
        }

        // La verificación de userId ya se realizó en onCreate, por lo que podemos usarlo directamente
        comprar.setOnClickListener {
            Toast.makeText(this, "Compra realizada para el usuario $userId", Toast.LENGTH_SHORT).show()
        }

        anadircarrito.setOnClickListener {
            Toast.makeText(this, "Añadido al carrito del usuario $userId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
