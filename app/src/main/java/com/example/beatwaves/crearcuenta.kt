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

class crearcuenta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_crearcuenta)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los campos de texto
        val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val passwordField = findViewById<EditText>(R.id.editTextTextEmailAddress3)

        // Referencias a los botones
        val volverButton = findViewById<Button>(R.id.crearvolver)
        val confirmarButton = findViewById<Button>(R.id.crearconfirmar)

        // Acción para el botón "Volver"
        volverButton.setOnClickListener {
            val intent = Intent(this@crearcuenta, MainActivity::class.java)
            startActivity(intent)
        }

        // Acción para el botón "Confirmar"
        confirmarButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Guardar los datos (aquí se simula el guardado)
                saveToDatabase(email, password)

                // Mostrar un mensaje de éxito
                Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()

                // Navegar a la siguiente actividad (catálogo de géneros)
                val intent = Intent(this@crearcuenta, catalogogeneros::class.java)
                startActivity(intent)
            } else {
                // Mostrar error si los campos están vacíos
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Función para guardar datos en la base de datos (simulación)
    private fun saveToDatabase(email: String, password: String) {
        val dbHelper = DatabaseHelper(this)
        val result = dbHelper.insertUser(email, password)

        if (result != -1L) {
            println("Usuario guardado correctamente con ID: $result")
        } else {
            println("Error al guardar el usuario")
        }

    }
    // Función para mostrar todos los usuarios guardados en la base de datos
    private fun showAllUsers() {
        val dbHelper = DatabaseHelper(this)
        val users = dbHelper.getAllUsers() // Obtener lista de usuarios

        println("Usuarios guardados en la base de datos:")
        if (users.isNotEmpty()) {
            users.forEach { user ->
                println("Email: ${user.first}, Contraseña: ${user.second}")
            }
        } else {
            println("No hay usuarios guardados.")
        }
    }
}




