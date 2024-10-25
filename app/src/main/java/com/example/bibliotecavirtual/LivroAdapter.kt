package com.example.bibliotecavirtual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class LivroAdapter(private val data: List<String>) : RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return LivroViewHolder(view)
    }


    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {

        holder.textView.text = data[position]
    }

    // Retorna a quantidade de itens na lista
    override fun getItemCount(): Int = data.size


    class LivroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(android.R.id.text1)
    }
}
