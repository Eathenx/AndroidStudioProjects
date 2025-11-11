package com.example.practica5login

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuario = findViewById<EditText>(R.id.etUsuario)
        val contraseña = findViewById<EditText>(R.id.etContrasena)
        val confirmar = findViewById<EditText>(R.id.etConfirmarContrasena)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val user = usuario.text.toString()
            val pass = contraseña.text.toString()
            val conf = confirmar.text.toString()

            if (user.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
                Toast.makeText(this, "Completa todo w", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass != conf) {
                Toast.makeText(this, "No w", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("usuarios", Context.MODE_PRIVATE)
            val usuariosJson = prefs.getString("usuarios", "{}")
            val usuariosObj = JSONObject(usuariosJson ?: "{}")

            if (usuariosObj.has(user)) {
                Toast.makeText(this, "Ese ya estaba w", Toast.LENGTH_SHORT).show()
            } else {
                usuariosObj.put(user, pass)
                prefs.edit().putString("usuarios", usuariosObj.toString()).apply()
                Toast.makeText(this, "Si jalo", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
