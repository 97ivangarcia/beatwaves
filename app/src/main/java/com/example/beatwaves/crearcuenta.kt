package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class crearcuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crearcuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton = findViewById<Button>(R.id.crearvolver);
        val boton2 = findViewById<Button>(R.id.crearconfirmar)

        boton.setOnClickListener{
            val intent = Intent(this@crearcuenta, MainActivity::class.java)
            startActivity(intent)
        }

        boton2.setOnClickListener{
            val intent = Intent(this@crearcuenta, catalogogeneros::class.java)
            startActivity(intent)
        }


    }
}