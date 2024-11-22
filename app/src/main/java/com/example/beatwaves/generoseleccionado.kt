package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class generoseleccionado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_generoseleccionado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val generoirhome = findViewById<ImageButton>(R.id.generoGoHome);
        val generoirsubida = findViewById<ImageButton>(R.id.generoGoSubir);
        val generoirperfil = findViewById<ImageButton>(R.id.generoGoPerfil);

        generoirhome.setOnClickListener {
            val intent = Intent(this@generoseleccionado, catalogogeneros::class.java)
            startActivity(intent)


        }
        generoirsubida.setOnClickListener {
            val intent = Intent(this@generoseleccionado, subircancion::class.java)
            startActivity(intent)


        }

        generoirperfil.setOnClickListener {
            val intent = Intent(this@generoseleccionado, tarjeta::class.java)
            startActivity(intent)


        }

    }
}