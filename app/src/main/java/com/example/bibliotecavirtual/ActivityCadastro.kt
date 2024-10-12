package com.example.bibliotecavirtual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityCadastroBinding

class ActivityCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aqui você pode adicionar lógica para o cadastro, se necessário
    }
}
