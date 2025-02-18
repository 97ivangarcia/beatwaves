package com.example.beatwaves

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

class comprarcanciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comprarcanciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // [1] Inicializar elementos de la interfaz
        val volvercatalogo = findViewById<Button>(R.id.botonvolver)
        val anadircarrito = findViewById<Button>(R.id.anadircarrito)
        val comprar = findViewById<Button>(R.id.comprar)
        val usuario = intent.getStringExtra("user")

        // [2] ►►► CÓDIGO PARA EL RECYCLERVIEW Y BASE DE DATOS ◄◄◄
        // Obtener datos de la BD
        val dbHelper = DatabaseHelper(this)
        val canciones = dbHelper.getAllCanciones()

        // Configurar RecyclerView
        val recycler = findViewById<RecyclerView>(R.id.recyclerCanciones)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CancionAdapter(canciones)
        // ◄◄◄ Fin del código del RecyclerView ►►►

        // [3] Listeners de los botones
        volvercatalogo.setOnClickListener{
            val intent = Intent(this@comprarcanciones, catalogogeneros::class.java)
            startActivity(intent)
        }

        comprar.setOnClickListener{
            if (usuario.equals("invitado")){
                Toast.makeText(this, "Inicia sesión para comprar.", Toast.LENGTH_SHORT).show()
            }else{
                // Lógica de compra
            }
        }

        anadircarrito.setOnClickListener{
            if (usuario.equals("invitado")){
                Toast.makeText(this, "Inicia sesión para añadir al carrito.", Toast.LENGTH_SHORT).show()
            }else{
                // Lógica del carrito
            }
        }
    }
}