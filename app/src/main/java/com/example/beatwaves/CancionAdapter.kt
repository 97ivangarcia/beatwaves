package com.example.beatwaves

import Cancion
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CancionAdapter(
    private val userId: Int,
    private val databaseHelper: DatabaseHelper,
    private val onFavoriteUpdated: () -> Unit
) : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

    private val canciones = mutableListOf<Cancion>()

    class CancionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenCancion: ImageView = view.findViewById(R.id.imagenCancion)
        val titulo: TextView = view.findViewById(R.id.textTitulo)
        val artista: TextView = view.findViewById(R.id.textArtista)
        val precio: TextView = view.findViewById(R.id.textPrecio)
        val botonLike: ImageButton = view.findViewById(R.id.botonLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cancion, parent, false)
        return CancionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val cancion = canciones[position]
        val context = holder.itemView.context

        val resId = context.resources.getIdentifier(
            cancion.imagenResName,
            "drawable",
            context.packageName
        )
        holder.imagenCancion.setImageResource(if (resId != 0) resId else R.drawable.heartpngfull)

        holder.titulo.text = cancion.titulo
        holder.artista.text = cancion.artista
        holder.precio.text = cancion.precio

        holder.botonLike.setImageResource(
            if (cancion.isLiked) R.drawable.heartpngfull else R.drawable.heartpng
        )

        holder.botonLike.setOnClickListener {
            if (userId == -1) {
                Toast.makeText(context, "Inicia sesión para guardar favoritos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoEstado = !cancion.isLiked
                if (databaseHelper.toggleFavorito(userId, cancion.id)) {
                    canciones[position].isLiked = nuevoEstado
                    notifyItemChanged(position)
                    onFavoriteUpdated()
                }
            }
        }
    }

    fun updateData(newCanciones: List<Cancion>) {
        canciones.clear()
        canciones.addAll(newCanciones)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = canciones.size
}
