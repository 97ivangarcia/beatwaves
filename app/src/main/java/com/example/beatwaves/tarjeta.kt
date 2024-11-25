package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class tarjeta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjeta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ircatalogo = findViewById<ImageButton>(R.id.tarjetaGoCatalogo);
        val irsubir = findViewById<ImageButton>(R.id.tarjetaGoSubida);
        val irperfil = findViewById<ImageButton>(R.id.tarjetaGoPerfil);
        val irlikes = findViewById<ImageButton>(R.id.tarjetaGoLikes);
        val ircarrito = findViewById<ImageButton>(R.id.tarjetaGoPago);


        //catalogogeneros, subircancion, perfiluser, likes, carrito
        ircatalogo.setOnClickListener {
            val intent = Intent(this@tarjeta, catalogogeneros::class.java)
            startActivity(intent)


        }
        irsubir.setOnClickListener {
            val intent = Intent(this@tarjeta, subircancion::class.java)
            startActivity(intent)


        }
        irperfil.setOnClickListener {
            val intent = Intent(this@tarjeta, perfiluser::class.java)
            startActivity(intent)


        }
        irlikes.setOnClickListener {
            val intent = Intent(this@tarjeta, likes::class.java)
            startActivity(intent)


        }
        ircarrito.setOnClickListener {
            val intent = Intent(this@tarjeta, carrito::class.java)
            startActivity(intent)


        }
    }
}