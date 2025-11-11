package com.example.practicacalificaciones

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var etUser: EditText
    lateinit var etPass: EditText
    var adminSQLite: AdminSQLite? = null
    var conector: SQLiteDatabase? = null
    var datos: ContentValues? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegister = findViewById<Button>(R.id.button_register)
        btnLogin = findViewById<Button>(R.id.button_login)
        etUser = findViewById(R.id.edit_text_username)
        etPass = findViewById(R.id.edit_text_password)


        btnRegister.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val user = etUser.text.toString()
            val pass = etPass.text.toString()

            if (user.isNotBlank() && pass.isNotBlank()) {
                adminSQLite = AdminSQLite(this, "CalificacionesDB", 1)
                conector = adminSQLite!!.writableDatabase
                val fila = conector!!.rawQuery("SELECT * FROM Usuario WHERE User = '$user' AND Password = '$pass'", null)

                if (fila.moveToFirst()) {
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                conector!!.close()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}