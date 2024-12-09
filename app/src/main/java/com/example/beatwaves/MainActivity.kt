package com.example.beatwaves

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val dbHelper = DatabaseHelper(this)
        val result = dbHelper.insertUser("admin", "admin")

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val bton = findViewById<Button>(R.id.iniciarsesion);

        val bton2 = findViewById<Button>(R.id.botoncuenta);

        val bton3 = findViewById<Button>(R.id.botoninvitado)

        bton.setOnClickListener{
            val intent = Intent(this@MainActivity, iniciarsesion::class.java)
            startActivity(intent)
        }

        bton2.setOnClickListener{
            val intent = Intent(this@MainActivity, crearcuenta::class.java)
            startActivity(intent)
        }

        bton3.setOnClickListener{
            val intent = Intent(this@MainActivity, catalogogeneros::class.java)
            intent.putExtra("user", "invitado")
            startActivity(intent)
        }

    }
}