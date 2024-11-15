package com.example.bibliotecavirtual

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ActivityCadastro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var nomeEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var cpfEditText: EditText
    private lateinit var celularEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var cadastrarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Inicializa os objetos Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inicializa os campos de entrada
        nomeEditText = findViewById(R.id.nomeEditText)
        emailEditText = findViewById(R.id.cadastroEmailEditText)
        cpfEditText = findViewById(R.id.cadastroCpfEditText)
        celularEditText = findViewById(R.id.cadastroCelularEditText)
        senhaEditText = findViewById(R.id.cadastroSenhaEditText)
        cadastrarButton = findViewById(R.id.CadastrarButton)

        // Define a ação para o botão de cadastro
        cadastrarButton.setOnClickListener {
            cadastrarUsuario()
        }
    }

    private fun cadastrarUsuario() {
        // Obtém os dados dos campos
        val nome = nomeEditText.text.toString()
        val email = emailEditText.text.toString()
        val cpf = cpfEditText.text.toString()
        val celular = celularEditText.text.toString()
        val senha = senhaEditText.text.toString()

        // Validação básica dos campos (exemplo)
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || celular.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        // Cadastrar usuário no Firebase Auth
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Salvar os dados do usuário no Firestore
                    val user = hashMapOf(
                        "nome" to nome,
                        "email" to email,
                        "cpf" to cpf,
                        "celular" to celular
                    )

                    db.collection("users")
                        .document(auth.currentUser!!.uid)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish() // Finaliza a Activity de cadastro
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Falha ao salvar dados no Firestore", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Erro ao cadastrar usuário: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
