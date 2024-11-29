package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavoritosActivity : AppCompatActivity() {

    private lateinit var tvTitulo: TextView
    private lateinit var btnAdicionarLista: ImageButton
    private lateinit var imgEmptyState: ImageView
    private lateinit var tvMensagem: TextView
    private lateinit var btnAdicionar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritos)


        tvTitulo = findViewById(R.id.tvTitulo)
        btnAdicionarLista = findViewById(R.id.btnAdicionarLista)
        imgEmptyState = findViewById(R.id.imgEmptyState)
        tvMensagem = findViewById(R.id.tvMensagem)
        btnAdicionar = findViewById(R.id.btnAdicionar)


        btnAdicionarLista.setOnClickListener {

            val intent = Intent(this, CriarListActivity::class.java)
            startActivity(intent)
        }


        mostrarTelaVazia()
    }

    private fun mostrarTelaVazia() {
        imgEmptyState.visibility = ImageView.VISIBLE
        tvMensagem.visibility = TextView.VISIBLE
        btnAdicionar.visibility = Button.VISIBLE
    }
}
