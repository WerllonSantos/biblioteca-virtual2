package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do botão de login
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        // Configuração do botão de cadastro
        binding.cadastrarButton.setOnClickListener {
            // Inicia a ActivityCadastro
            val intent = Intent(this, ActivityCadastro::class.java)
            startActivity(intent) // Chama a ActivityCadastro
        }
    }
}
