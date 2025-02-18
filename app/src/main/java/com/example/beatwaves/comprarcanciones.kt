package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class comprarcanciones : AppCompatActivity() {

    private var isFavorite = false // Estado inicial (corazón vacío)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comprarcanciones)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val volvercatalogo = findViewById<Button>(R.id.botonvolver)
        val anadircarrito = findViewById<Button>(R.id.anadircarrito)
        val comprar = findViewById<Button>(R.id.comprar)
        val heartButton = findViewById<ImageButton>(R.id.heartButton) // Botón del corazón

        val usuario = intent.getStringExtra("user")

        volvercatalogo.setOnClickListener {
            val intent = Intent(this@comprarcanciones, catalogogeneros::class.java)
            startActivity(intent)
        }

        comprar.setOnClickListener {
            if (usuario.equals("invitado")) {
                Toast.makeText(this, "Inicia sesión para comprar.", Toast.LENGTH_SHORT).show()
            } else {
                // Espacio para la función del botón
            }
        }

        anadircarrito.setOnClickListener {
            if (usuario.equals("invitado")) {
                Toast.makeText(this, "Inicia sesión para añadir al carrito.", Toast.LENGTH_SHORT).show()
            } else {
                // Espacio para la función del botón
            }
        }

        // 💖 Lógica del botoncito del corazón
        heartButton.setOnClickListener {
            isFavorite = !isFavorite // Cambia el estado

            if (isFavorite) {
                heartButton.setImageResource(R.drawable.heartpngfull) // Corazón lleno
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            } else {
                heartButton.setImageResource(R.drawable.heartpng) // Corazón vacío
                Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
