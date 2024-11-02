package com.example.bibliotecavirtual

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityCadastroBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class ActivityCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão para confirmar o cadastro
         binding.CadastrarButton.setOnClickListener {
            val nome = binding.nomeEditText.text.toString()
            val email = binding.cadastroEmailEditText.text.toString()
            val cpf = binding.cadastroCpfEditText.text.toString()
            val celular = binding.cadastroCelularEditText.text.toString()

            // Verifica se todos os campos foram preenchidos
            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || celular.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                cadastrarUsuario(nome, email, cpf, celular)
            }
        }
    }

    // Função para cadastrar o usuário no Firestore
    private fun cadastrarUsuario(nome: String, email: String, cpf: String, celular: String) {
        // Mapa de dados do usuário
        val user = hashMapOf(
            "nome" to nome,
            "email" to email,
            "cpf" to cpf,
            "celular" to celular
        )

        db.collection("usuarios") // Adiciona o usuário à coleção "usuarios"
            .add(user)
            .addOnSuccessListener {
                Log.d(TAG, "Cadastro realizado com sucesso!")
            }

            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        limparCampos()

        // Redireciona para a tela de login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finaliza a Activity atual para que o usuário não retorne a ela
    }


    // Função para limpar os campos após o cadastro
    private fun limparCampos() {
        binding.nomeEditText.text.clear()
        binding.cadastroEmailEditText.text.clear()
        binding.cadastroCpfEditText.text.clear()
        binding.cadastroCelularEditText.text.clear()
    }
}
