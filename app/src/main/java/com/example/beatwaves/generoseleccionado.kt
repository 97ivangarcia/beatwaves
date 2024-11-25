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
        val botonartista1 = findViewById<Button>(R.id.generoboton1);
        val botonartista2 = findViewById<Button>(R.id.generoboton2);
        val botonartista3 = findViewById<Button>(R.id.generoboton3);
        val botonartista4 = findViewById<Button>(R.id.generoboton4);

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


        //BOTONES TIENDA ARTISTAS

        botonartista1.setOnClickListener {
            val intent = Intent(this@generoseleccionado, comprarcanciones::class.java)
            startActivity(intent)


        }
        botonartista2.setOnClickListener {
            val intent = Intent(this@generoseleccionado, comprarcanciones::class.java)
            startActivity(intent)


        }
        botonartista3.setOnClickListener {
            val intent = Intent(this@generoseleccionado, comprarcanciones::class.java)
            startActivity(intent)


        }
        botonartista4.setOnClickListener {
            val intent = Intent(this@generoseleccionado, comprarcanciones::class.java)
            startActivity(intent)


        }

    }
}