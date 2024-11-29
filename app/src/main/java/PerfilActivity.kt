package com.example.bibliotecavirtual

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class PerfilActivity : AppCompatActivity() {

    private var _binding: ActivityPerfilBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 171089
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private lateinit var auth: FirebaseAuth
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "usuarioID1"
    private var profileImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()


        mostrarDadosUsuario()


        binding.btnfoto.setOnClickListener {
            dispatchTakePictureIntent()
        }


        binding.buttonSave.setOnClickListener {
            salvarDados()
        }


        binding.buttonSair.setOnClickListener {
            logoutUser()
        }
    }


    private fun mostrarDadosUsuario() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val nome = document.getString("nome")
                        val email = document.getString("email")
                        val celular = document.getString("celular")
                        val imageUrl = document.getString("fotoPerfil")


                        binding.editName.setText(nome)
                        binding.editEmail.setText(email)
                        binding.editPhone.setText(celular)


                        imageUrl?.let {

                        }

                        Log.d(TAG, "Dados do usuário: $nome, $email, $celular, $imageUrl")
                    } else {
                        Log.d(TAG, "Usuário não encontrado")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Erro ao recuperar dados do usuário: ", exception)
                    Toast.makeText(this, "Falha ao recuperar dados do usuário", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun salvarDados() {
        val nome = binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val celular = binding.editPhone.text.toString()
        val senha = binding.editSenha.text.toString()


        val user = auth.currentUser
        if (user != null) {

            val profileUpdates = userProfileChangeRequest {
                displayName = nome
            }


            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Perfil atualizado no Firebase Authentication")

                        //
                        if (email != user.email) {
                            user.updateEmail(email).addOnCompleteListener { emailTask ->
                                if (emailTask.isSuccessful) {
                                    Log.d(TAG, "E-mail atualizado no Firebase Authentication")

                                    if (senha.isNotEmpty()) {
                                        user.updatePassword(senha).addOnCompleteListener { passwordTask ->
                                            if (passwordTask.isSuccessful) {
                                                Log.d(TAG, "Senha atualizada no Firebase Authentication")

                                                updateProfileImage(user, nome, email, celular)
                                            } else {
                                                Toast.makeText(this, "Falha ao atualizar senha", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {

                                        updateProfileImage(user, nome, email, celular)
                                    }
                                } else {
                                    Toast.makeText(this, "Falha ao atualizar e-mail", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {

                            updateProfileImage(user, nome, email, celular)
                        }
                    } else {
                        Toast.makeText(this, "Falha ao atualizar o perfil", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    private fun updateProfileImage(user: FirebaseUser, nome: String, email: String, celular: String) {
        if (profileImageUri != null) {
            val storageRef = storage.reference.child("profile_pictures/${user.uid}.jpg")
            val uploadTask = storageRef.putFile(profileImageUri!!)

            uploadTask.addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->

                    val userMap = hashMapOf(
                        "nome" to nome,
                        "email" to email,
                        "celular" to celular,
                        "fotoPerfil" to uri.toString()
                    )

                    db.collection("users")
                        .document(user.uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Falha ao salvar dados no Firestore", e)
                            Toast.makeText(this, "Falha ao salvar dados", Toast.LENGTH_SHORT).show()
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Falha ao fazer upload da foto", Toast.LENGTH_SHORT).show()
            }
        } else {

            val userMap = hashMapOf(
                "nome" to nome,
                "email" to email,
                "celular" to celular
            )

            db.collection("users")
                .document(user.uid)
                .set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Falha ao salvar dados no Firestore", e)
                    Toast.makeText(this, "Falha ao salvar dados", Toast.LENGTH_SHORT).show()
                }
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


            val bytes = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(contentResolver, imageBitmap, "title", null)
            profileImageUri = Uri.parse(path)
        }
    }


    private fun logoutUser() {
        auth.signOut()


        Toast.makeText(this, "Você saiu com sucesso!", Toast.LENGTH_SHORT).show()


        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
