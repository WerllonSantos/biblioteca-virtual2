package com.example.bibliotecavirtual

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : AppCompatActivity() {

    private var _binding: ActivityPerfilBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 171089
    private val db = FirebaseFirestore.getInstance() // Inicializa o Firestore
    private lateinit var auth: FirebaseAuth
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "usuarioID1" // Usar o ID do usuário atual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Chama a função para mostrar os dados do usuário
        mostrarDadosUsuario()

        // Configura o botão de tirar foto
        binding.btnfoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // Configura o botão de salvar
        binding.buttonSave.setOnClickListener {
            salvarDados()
        }

        // Configura o botão de logout
        binding.buttonSair.setOnClickListener {
            logoutUser()
        }
    }

    // Função para mostrar os dados do usuário no Firestore
    private fun mostrarDadosUsuario() {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val nome = document.getString("nome")
                    val email = document.getString("email")
                    val celular = document.getString("celular")

                    // Exibe os dados no layout
                    binding.editName.setText(nome)
                    binding.editEmail.setText(email)
                    binding.editPhone.setText(celular)

                    Log.d(TAG, "Dados do usuário: $nome, $email, $celular")
                } else {
                    Log.d(TAG, "Usuário não encontrado")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "Falha ao recuperar dados do usuário", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para salvar os dados editados do usuário
    private fun salvarDados() {
        val nome = binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val celular = binding.editPhone.text.toString()
        val senha = binding.editSenha.text.toString()

        val userMap = hashMapOf(
            "nome" to nome,
            "email" to email,
            "celular" to celular,
            "senha" to senha
        )

        db.collection("users")
            .document(userId)
            .set(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Failed to save data.", e)
                Toast.makeText(this, "Falha ao salvar dados", Toast.LENGTH_SHORT).show()
            }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Recebe a imagem capturada e exibe na ImageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.perfilImage.setImageBitmap(imageBitmap)
        }
    }

    // Função para realizar o logout
    private fun logoutUser() {
        // Desloga o usuário do Firebase
        auth.signOut()

        // Exibe uma mensagem de sucesso
        Toast.makeText(this, "Você saiu com sucesso!", Toast.LENGTH_SHORT).show()

        // Redireciona o usuário para a tela de login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Fecha a tela atual
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}