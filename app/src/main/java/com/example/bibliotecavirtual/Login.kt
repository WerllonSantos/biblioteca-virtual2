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

        // Inicializa o binding da view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        updateUI(currentUser)


        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val senha = binding.senhaEditText.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                loginUser(email, senha)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }


        binding.cadastrarButton.setOnClickListener {
            val intent = Intent(this, ActivityCadastro::class.java)
            startActivity(intent)
        }
    }


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


    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}
