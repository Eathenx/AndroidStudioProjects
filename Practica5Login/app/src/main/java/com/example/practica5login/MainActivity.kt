package com.example.practica5login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usuario = findViewById<EditText>(R.id.etUser)
        val contraseña = findViewById<EditText>(R.id.etPassword)
        val btnIngresar = findViewById<Button>(R.id.btnLogin)
        val btnRegistrar = findViewById<Button>(R.id.btnRegister)

        btnIngresar.setOnClickListener {
            val user = usuario.text.toString()
            val pass = contraseña.text.toString()

            val prefs = getSharedPreferences("usuarios", Context.MODE_PRIVATE)
            val usuariosJson = prefs.getString("usuarios", "{}")
            val usuariosObj = JSONObject(usuariosJson ?: "{}")

            if (usuariosObj.has(user) && usuariosObj.getString(user) == pass) {
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "tas mal en algo", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}
