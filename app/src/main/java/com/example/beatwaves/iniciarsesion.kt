package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class iniciarsesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciarsesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonVolver = findViewById<Button>(R.id.iniciarvolver)
        val botonIniciar = findViewById<Button>(R.id.iniciarsiguiente)

        // Acción para el botón "Volver"
        botonVolver.setOnClickListener {
            val intent = Intent(this@iniciarsesion, MainActivity::class.java)
            startActivity(intent)
        }

        // Acción para el botón "Iniciar Sesión"
        botonIniciar.setOnClickListener {
            val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress2)
            val passwordField = findViewById<EditText>(R.id.editTextTextEmailAddress3)

            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val dbHelper = DatabaseHelper(this)
                val isValidUser = dbHelper.validateUser(email, password)

                if (isValidUser) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@iniciarsesion, catalogogeneros::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
