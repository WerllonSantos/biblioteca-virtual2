package com.example.bibliotecavirtual

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecavirtual.databinding.ActivityLoginBinding
import androidx.fragment.app.Fragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {

            val intent = Intent(this, inicio::class.java)
            startActivity(intent)
        }

        binding.cadastrarButton.setOnClickListener {
            openCadastroFragment()
        }


        binding.fragmentContainerCadastro.visibility = View.GONE
    }


    private fun openCadastroFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerCadastro, CadastroFragment())
            .addToBackStack(null)
            .commit()


        binding.fragmentContainerCadastro.visibility = View.VISIBLE
    }
}