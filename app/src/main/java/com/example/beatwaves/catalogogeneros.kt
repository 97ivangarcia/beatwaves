package com.example.beatwaves

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout


class catalogogeneros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogogeneros)

        // Obtener la referencia al ConstraintLayout principal
        val mainLayout: ConstraintLayout = findViewById(R.id.main)

        // Ajustar el padding de la vista principal según las insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            // Obtener los márgenes de las insets de las barras del sistema (barra de estado y barra de navegación)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Establecer el padding superior, izquierdo, derecho e inferior
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Devolver las insets para que se apliquen correctamente
            insets
        }


        val botonusuario = findViewById<ImageButton>(R.id.navigation_user);
        val botonsubida = findViewById<ImageButton>(R.id.botonsubida);
        val botonlikes = findViewById<ImageButton>(R.id.navigation_heart);
        val botoncarritomain = findViewById<ImageButton>(R.id.navigation_shopping_bag);

        //unico boton accesible en invitado

        val botonsitodnb = findViewById<Button>(R.id.botondnb);

        val usuario = intent.getStringExtra("user")


        botonsitodnb.setOnClickListener {
            val intent = Intent(this@catalogogeneros, generoseleccionado::class.java)
            intent.putExtra("user", "invitado")
            startActivity(intent)


        }


        if (!usuario.equals("invitado")){
        botoncarritomain.setOnClickListener {
            val intent = Intent(this@catalogogeneros, carrito::class.java)
            startActivity(intent)


        }

        botonusuario.setOnClickListener {
            val intent = Intent(this@catalogogeneros, perfiluser::class.java)
            startActivity(intent)


        }
        botonlikes.setOnClickListener {
            val intent = Intent(this@catalogogeneros, likes::class.java)
            startActivity(intent)


        }

        botonsubida.setOnClickListener {
            val intent = Intent(this@catalogogeneros, subircancion::class.java)
            startActivity(intent)

        }
        }
    }
}
