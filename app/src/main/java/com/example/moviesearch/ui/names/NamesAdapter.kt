package com.example.moviesearch.ui.names

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.domain.models.Name

class NamesAdapter : RecyclerView.Adapter<NameViewHolder>() {

    val names = ArrayList<Name>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder = NameViewHolder(parent)

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(names[position])
    }

    override fun getItemCount(): Int = names.size
}