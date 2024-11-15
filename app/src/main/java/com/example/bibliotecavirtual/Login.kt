package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Verifica se já há um usuário logado
        val currentUser = auth.currentUser
        updateUI(currentUser)

        // Configura o botão de login
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val senha = binding.senhaEditText.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                loginUser(email, senha)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o botão de cadastro
        binding.cadastrarButton.setOnClickListener {
            val intent = Intent(this, ActivityCadastro::class.java)
            startActivity(intent)
        }
    }

    // Função para realizar o login do usuário
    private fun loginUser(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Falha ao fazer login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Atualiza a UI dependendo do estado de login do usuário
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Se o usuário estiver logado, redireciona para a tela principal (Home)
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()  // Finaliza a tela de login
        }
    }

    // Verifica se o usuário já está logado ao iniciar a Activity
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}
