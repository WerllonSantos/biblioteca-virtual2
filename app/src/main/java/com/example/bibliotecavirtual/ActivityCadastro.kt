package com.example.bibliotecavirtual

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityCadastroBinding
import com.google.firebase.firestore.FirebaseFirestore

class ActivityCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val db = FirebaseFirestore.getInstance() // Inicializa o Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão para confirmar o cadastro
        binding.CadastrarButton.setOnClickListener {
            // Captura os dados inseridos pelo usuário
            val nome = binding.nomeEditText.text.toString()
            val email = binding.cadastroEmailEditText.text.toString()
            val cpf = binding.cadastroCpfEditText.text.toString()
            val celular = binding.cadastroCelularEditText.text.toString()

            // Verificação básica de campos preenchidos
            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || celular.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
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

        db.collection("usuarios") // Define a coleção "usuarios" no Firestore
            .add(user) // Adiciona o usuário
            .addOnSuccessListener {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Limpa os campos após o cadastro
                limparCampos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao cadastrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para limpar os campos após o cadastro
    private fun limparCampos() {
        binding.nomeEditText.text.clear()
        binding.cadastroEmailEditText.text.clear()
        binding.cadastroCpfEditText.text.clear()
        binding.cadastroCelularEditText.text.clear()
    }
}
