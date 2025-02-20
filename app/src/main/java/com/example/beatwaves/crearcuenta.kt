package com.example.beatwaves

import android.content.Context
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

        val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val passwordField = findViewById<EditText>(R.id.editTextTextEmailAddress3)
        val volverButton = findViewById<Button>(R.id.crearvolver)
        val confirmarButton = findViewById<Button>(R.id.crearconfirmar)

        volverButton.setOnClickListener {
            val intent = Intent(this@crearcuenta, MainActivity::class.java)
            startActivity(intent)
        }

        confirmarButton.setOnClickListener {
            // Convertir email a minúsculas y eliminar espacios
            val email = emailField.text.toString().trim().lowercase()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Guardar datos en la base de datos y almacenar userId
                saveToDatabase(email, password)
                Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@crearcuenta, catalogogeneros::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun saveToDatabase(email: String, password: String) {
        val dbHelper = DatabaseHelper(this)
        val result = dbHelper.insertUser(email, password)
        if (result != -1L) {
            dbHelper.getUserByEmail(email).use { cursor ->
                if (cursor.moveToFirst()) {
                    val userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))

                    // Guardar userId en SharedPreferences
                    val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    prefs.edit().putInt("userId", userId).apply()

                    Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@crearcuenta, catalogogeneros::class.java)
                    startActivity(intent)
                    finish() // Cierra la actividad de registro
                }
            }
        } else {
            Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show()
        }
    }
}