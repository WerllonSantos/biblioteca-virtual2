package com.example.bibliotecavirtual

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CriarLista : AppCompatActivity() {

    private lateinit var edtNomeLista: EditText
    private lateinit var edtDescricaoLista: EditText
    private lateinit var tvContadorNome: TextView
    private lateinit var tvContadorDescricao: TextView
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_lista)


        db = FirebaseFirestore.getInstance()

        edtNomeLista = findViewById(R.id.edt_nome_lista)
        edtDescricaoLista = findViewById(R.id.edt_descricao_lista)
        tvContadorNome = findViewById(R.id.tv_contador_nome)
        tvContadorDescricao = findViewById(R.id.tv_contador_descricao)
        btnSalvar = findViewById(R.id.btn_salvar)
        btnCancelar = findViewById(R.id.btn_cancelar)


        edtNomeLista.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tvContadorNome.text = "${s?.length ?: 0}/255"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        edtDescricaoLista.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tvContadorDescricao.text = "${s?.length ?: 0}/300"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        btnCancelar.setOnClickListener {
            finish()
        }


        btnSalvar.setOnClickListener {
            val nomeLista = edtNomeLista.text.toString().trim()
            val descricaoLista = edtDescricaoLista.text.toString().trim()

            if (nomeLista.isNotEmpty()) {

                val listaData = hashMapOf(
                    "nome" to nomeLista,
                    "descricao" to descricaoLista
                )


                db.collection("listas")
                    .add(listaData)
                    .addOnSuccessListener { documentReference ->
                        showMessage("Lista '$nomeLista' salva com sucesso!")
                        finish()
                    }
                    .addOnFailureListener { e ->
                        showMessage("Erro ao salvar a lista: ${e.message}")
                    }
            } else {
                showMessage("O nome da lista é obrigatório!")
            }
        }
    }


    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}