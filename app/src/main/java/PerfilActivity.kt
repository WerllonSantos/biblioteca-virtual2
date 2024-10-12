package com.example.bibliotecavirtual

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {

    private var _binding: ActivityPerfilBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 171089

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão para abrir a câmera
        binding.btnfoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    // Método para abrir a câmera
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Verifica se existe uma atividade que pode lidar com a intenção
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Lida com o resultado da captura de imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Obtém a imagem capturada
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Exibe a imagem na ImageView
            binding.perfilImage.setImageBitmap(imageBitmap)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Limpa o binding
    }
}
