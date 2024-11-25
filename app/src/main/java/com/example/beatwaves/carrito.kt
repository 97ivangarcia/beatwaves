package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class carrito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val carritotohome = findViewById<ImageButton>(R.id.carritohome);
        val carritotosubir = findViewById<ImageButton>(R.id.carritosubidas);
        val carritotolikes = findViewById<ImageButton>(R.id.carritolikes);
        val carritotoperfil = findViewById<ImageButton>(R.id.carritoperfil);

        carritotohome.setOnClickListener {
            val intent = Intent(this@carrito, catalogogeneros::class.java)
            startActivity(intent)


        }
        carritotosubir.setOnClickListener {
            val intent = Intent(this@carrito, subircancion::class.java)
            startActivity(intent)


        }
        carritotolikes.setOnClickListener {
            val intent = Intent(this@carrito, likes::class.java)
            startActivity(intent)


        }
        carritotoperfil.setOnClickListener {
            val intent = Intent(this@carrito, perfiluser::class.java)
            startActivity(intent)


        }
    }
}