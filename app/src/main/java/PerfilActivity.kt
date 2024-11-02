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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PerfilActivity : AppCompatActivity() {

    private var _binding: ActivityPerfilBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 171089
    private val db = FirebaseFirestore.getInstance() // Inicializa o Firestore
    private val userId = "usuarioID1" // Substitua pelo ID do usuário atual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Chama a função para mostrar os dados do usuário
        mostrarDadosUsuario()

        binding.btnfoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.buttonSave.setOnClickListener {
            // Salva uma mensagem no Firestore
            db.collection("message").document("status")
                .set(mapOf("status" to "Dados editados com sucesso"))
                .addOnSuccessListener {
                    Toast.makeText(this, "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w("PerfilActivity", "Failed to save data.", e)
                }
        }
    }

    // Função para mostrar os dados do usuário
    private fun mostrarDadosUsuario() {

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("PerfilActivity", "Error getting documents: ", exception)
                Toast.makeText(this, "Falha ao recuperar dados do usuário", Toast.LENGTH_SHORT).show()
            }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.perfilImage.setImageBitmap(imageBitmap)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
