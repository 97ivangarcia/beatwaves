package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class perfiluser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfiluser)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val home = findViewById<ImageButton>(R.id.perfiluserGoHome);
        val subir = findViewById<ImageButton>(R.id.perfiluserGoSubir);
        val iralikes = findViewById<ImageButton>(R.id.perfiluserGoLikes);
        val iracarrito = findViewById<ImageButton>(R.id.perfiluserGoCompra);
        val editperfil = findViewById<Button>(R.id.botoneditarperfil);

        home.setOnClickListener {
            val intent = Intent(this@perfiluser, catalogogeneros::class.java)
            startActivity(intent)


        }

        iracarrito.setOnClickListener {
            val intent = Intent(this@perfiluser, carrito::class.java)
            startActivity(intent)


        }

        subir.setOnClickListener {
            val intent = Intent(this@perfiluser, subircancion::class.java)
            startActivity(intent)


        }

        editperfil.setOnClickListener {
            val intent = Intent(this@perfiluser, tarjeta::class.java)
            startActivity(intent)


        }

        iralikes.setOnClickListener {
            val intent = Intent(this@perfiluser, likes::class.java)
            startActivity(intent)


        }
    }
}
