package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate o layout com ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ação do botão "Iniciar Sessão"
        binding.loginButton.setOnClickListener {
            // Cria uma Intent para iniciar a tela de início (InicioActivity)
            val intent = Intent(this, inicio::class.java)
            startActivity(intent)  // Inicia a tela de início
        }

        // Ação do botão "Cadastrar"
        binding.cadastrarButton.setOnClickListener {
            openCadastroFragment()
        }

        // Torna o FrameLayout invisível inicialmente
        binding.fragmentContainer.visibility = View.GONE
    }

    // Função para abrir o fragmento de cadastro
    private fun openCadastroFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CadastroFragment()) // Substitui pelo fragmento de cadastro
            .addToBackStack(null) // Permite voltar à tela anterior
            .commit()

        // Torna o FrameLayout visível
        binding.fragmentContainer.visibility = View.VISIBLE
    }
}
