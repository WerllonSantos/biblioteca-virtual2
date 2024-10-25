package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_perfil -> {
                    val intent =
                        Intent(
                            this@Home,
                            PerfilActivity::class.java
                        )
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        fun configurarRecyclerView(findViewById: Any, livrosPopulares: List<String>) {
            TODO("Not yet implemented")
        }

        fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)


            val livrosPopulares = listOf("Livro 1", "Livro 2", "Livro 3", "Livro 4", "Livro 5")
            val novidades = listOf("Novo 1", "Novo 2", "Novo 3", "Novo 4", "Novo 5")
            val recomendados =
                listOf("Recomendado 1", "Recomendado 2", "Recomendado 3", "Recomendado 4")


            configurarRecyclerView(findViewById(R.id.recyclerView1), livrosPopulares)
            configurarRecyclerView(findViewById(R.id.recyclerView2), novidades)
            configurarRecyclerView(findViewById(R.id.recyclerView3), recomendados)
        }

        fun configurarRecyclerView(recyclerView: RecyclerView, data: List<String>) {
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = LivroAdapter(data)
        }
    }
}