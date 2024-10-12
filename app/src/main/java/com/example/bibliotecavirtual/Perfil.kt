package com.example.bibliotecavirtual

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Perfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil) // Verifique se este layout existe
        Log.d("PerfilActivity", "Activity de perfil iniciada")
    }
}
