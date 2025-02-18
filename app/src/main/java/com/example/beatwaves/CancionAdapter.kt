package com.example.beatwaves

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CancionAdapter(private val canciones: List<Cancion>) :
    RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {

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

        // Convertir nombre de imagen (String) a ID de recurso (Int)
        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(cancion.imagenResName, "drawable", context.packageName)

        if (resId != 0) {
            holder.imagenCancion.setImageResource(resId) // Asigna la imagen si se encuentra
        } else {
            holder.imagenCancion.setImageResource(R.drawable.heartpngfull) // Imagen por defecto si no existe
        }

        holder.titulo.text = cancion.titulo
        holder.artista.text = cancion.artista
        holder.precio.text = cancion.precio

        // Configurar botón de like
        holder.botonLike.setImageResource(
            if (cancion.isLiked) R.drawable.heartpngfull else R.drawable.heartpng
        )

        holder.botonLike.setOnClickListener {
            cancion.isLiked = !cancion.isLiked
            notifyItemChanged(position)
        }
    }


    override fun getItemCount(): Int = canciones.size
}
