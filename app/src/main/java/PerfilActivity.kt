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


        binding.btnfoto.setOnClickListener {
            dispatchTakePictureIntent()
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
