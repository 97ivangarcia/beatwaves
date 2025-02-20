package com.example.beatwaves

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class perfiluser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfiluser)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, iniciarsesion::class.java))
            finish()
            return
        }

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val query = "SELECT ${DatabaseHelper.COLUMN_EMAIL} FROM ${DatabaseHelper.TABLE_USERS} WHERE ${DatabaseHelper.COLUMN_ID} = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val textViewUser = findViewById<TextView>(R.id.textView7)

        if (cursor.moveToFirst()) {
            val email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL))
            textViewUser.text = "$email\nID: $userId"
        } else {
            textViewUser.text = "Usuario no encontrado"
        }
        cursor.close()

        val home = findViewById<ImageButton>(R.id.perfiluserGoHome)
        val subir = findViewById<ImageButton>(R.id.perfiluserGoSubir)
        val iralikes = findViewById<ImageButton>(R.id.perfiluserGoLikes)
        val iracarrito = findViewById<ImageButton>(R.id.perfiluserGoCompra)
        val editperfil = findViewById<Button>(R.id.botoneditarperfil)

        home.setOnClickListener {
            val intent = Intent(this@perfiluser, catalogogeneros::class.java)
            startActivity(intent)
        }

        iracarrito.setOnClickListener {
            val intent = Intent(this@perfiluser, carrito::class.java)
            startActivity(intent)
        }

        subir.setOnClickListener {
            val intent = Intent(this@perfiluser, subircancion::class.java)
            startActivity(intent)
        }

        editperfil.setOnClickListener {
            val intent = Intent(this@perfiluser, tarjeta::class.java)
            startActivity(intent)
        }

        iralikes.setOnClickListener {
            val intent = Intent(this@perfiluser, likes::class.java)
            startActivity(intent)
        }

        // Configuración para cambiar la imagen de perfil
        val imageViewPerfil = findViewById<ImageView>(R.id.perfiluserImagen)

        val images = arrayOf(
            R.drawable.deekline,
            R.drawable.edsolo,
            R.drawable.charlotte,
            R.drawable.richie,
            R.drawable.georgie,
            R.drawable.skrillex
        )

        // Cargar la imagen guardada en SharedPreferences
        val savedImage = prefs.getInt("profileImage", R.drawable.deekline)
        imageViewPerfil.setImageResource(savedImage)

        imageViewPerfil.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona una imagen de perfil")

            val gridView = GridView(this).apply {
                numColumns = 3
                adapter = object : BaseAdapter() {
                    override fun getCount() = images.size
                    override fun getItem(position: Int) = images[position]
                    override fun getItemId(position: Int) = position.toLong()
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val imgView = ImageView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(200, 200)
                            setImageResource(images[position])
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                        return imgView
                    }
                }
            }

            builder.setView(gridView)
            val dialog = builder.create()
            dialog.show()

            gridView.setOnItemClickListener { _, _, position, _ ->
                val selectedImage = images[position]
                imageViewPerfil.setImageResource(selectedImage)

                // Guardar selección en SharedPreferences
                prefs.edit().putInt("profileImage", selectedImage).apply()
                dialog.dismiss()
            }
        }
    }
}
