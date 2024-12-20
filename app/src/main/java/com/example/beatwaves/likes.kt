package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class likes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_likes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val likestohome = findViewById<ImageButton>(R.id.likesGoHome);
        val likestosubir = findViewById<ImageButton>(R.id.likesGoSubir);
        val likestocarrito = findViewById<ImageButton>(R.id.likesGoCompra);
        val likestoperfil = findViewById<ImageButton>(R.id.likesGoPerfil);

        likestohome.setOnClickListener {
            val intent = Intent(this@likes, catalogogeneros::class.java)
            startActivity(intent)


        }
        likestosubir.setOnClickListener {
            val intent = Intent(this@likes, subircancion::class.java)
            startActivity(intent)


        }
        likestocarrito.setOnClickListener {
            val intent = Intent(this@likes, carrito::class.java)
            startActivity(intent)


        }
        likestoperfil.setOnClickListener {
            val intent = Intent(this@likes, perfiluser::class.java)
            startActivity(intent)


        }

    }
}