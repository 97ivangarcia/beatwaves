package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        val botongenero1 = findViewById<Button>(R.id.generoboton1);
        val botongenero2 = findViewById<Button>(R.id.generoboton2);
        val botongenero3 = findViewById<Button>(R.id.generoboton3);
        val botongenero4 = findViewById<Button>(R.id.generoboton4);


        //AQUI SE CONFIGURARÁN LOS BOTONES CUANDO CONECTEMOS LA BASE DE DATOS

        botongenero1.setOnClickListener {
            val intent = Intent(this@generoseleccionado, perfiluser::class.java)
            startActivity(intent)


        }

        botongenero2.setOnClickListener {
            val intent = Intent(this@generoseleccionado, perfiluser::class.java)
            startActivity(intent)


        }
        botongenero3.setOnClickListener {
            val intent = Intent(this@generoseleccionado, perfiluser::class.java)
            startActivity(intent)


        }
        botongenero4.setOnClickListener {
            val intent = Intent(this@generoseleccionado, perfiluser::class.java)
            startActivity(intent)


        }
    }
}