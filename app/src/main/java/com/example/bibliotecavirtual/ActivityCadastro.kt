package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.CadastrarButton.setOnClickListener {
            val nome = binding.nomeEditText.text.toString()
            val email = binding.cadastroEmailEditText.text.toString()
            val cpf = binding.cadastroCpfEditText.text.toString()
            val celular = binding.cadastroCelularEditText.text.toString()
            val senha = binding.cadastroSenhaEditText.text.toString()

            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || celular.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                cadastrarUsuario(email, senha)
            }
        }
    }

    // Função para realizar o cadastro
    private fun cadastrarUsuario(email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //val user = auth.currentUser
                    //Log.d("Firebase", "Usuário registrado: ${user?.email}")
                    Toast.makeText(this, "Registro bem-sucedido", Toast.LENGTH_SHORT).show()

                    // Após o cadastro bem-sucedido, redireciona para a tela de login
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Finaliza a ActivityCadastro
                } else {
                    // Se o cadastro falhar, exibe a mensagem de erro
                    Log.w("Firebase", "Falha ao registrar usuário", task.exception)
                    Toast.makeText(this, "Falha ao registrar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
