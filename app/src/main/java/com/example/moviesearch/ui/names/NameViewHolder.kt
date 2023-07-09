package com.example.moviesearch.ui.names

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.domain.models.Name

class NameViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_name, parent, false)
) {

    private val imageView: ImageView = itemView.findViewById(R.id.name_image)
    private val nameTextView: TextView = itemView.findViewById(R.id.name)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.name_description)

    fun bind(name: Name) {

        Glide.with(itemView)
            .load(name.imageUrl)
            .circleCrop()
            .into(imageView)

        nameTextView.text = name.name
        descriptionTextView.text = name.description
    }


}