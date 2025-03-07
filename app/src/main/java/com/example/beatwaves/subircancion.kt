package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class subircancion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_subircancion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val perfil = findViewById<ImageButton>(R.id.botonperfil);
        val home = findViewById<ImageButton>(R.id.botoncatalogo);
        val subidas = findViewById<ImageButton>(R.id.botonsubida);
        val pagos = findViewById<ImageButton>(R.id.botonpago);
        val likessubir = findViewById<ImageButton>(R.id.botonmegustas);
        val subirdatoscancion = findViewById<Button>(R.id.botonsubirlosdatos);



        //catalogogeneros, subircancion, perfiluser, likes, carrito
        perfil.setOnClickListener {
            val intent = Intent(this@subircancion, perfiluser::class.java)
            startActivity(intent)


        }
        subidas.setOnClickListener {
            val intent = Intent(this@subircancion, subircancion::class.java)
            startActivity(intent)


        }
        pagos.setOnClickListener {
            val intent = Intent(this@subircancion, carrito::class.java)
            startActivity(intent)


        }
        likessubir.setOnClickListener {
            val intent = Intent(this@subircancion, likes::class.java)
            startActivity(intent)


        }
        home.setOnClickListener {
            val intent = Intent(this@subircancion, catalogogeneros::class.java)
            startActivity(intent)


        }



        subirdatoscancion.setOnClickListener {
            val intent = Intent(this@subircancion, datossubida::class.java)
            startActivity(intent)


        }

    }
}