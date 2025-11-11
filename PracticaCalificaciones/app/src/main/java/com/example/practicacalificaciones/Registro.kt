package com.example.practicacalificaciones

import android.content.ContentValues
import android.content.Context
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

class Registro : AppCompatActivity() {
    lateinit var btnRegresar: Button
    lateinit var btnRegister: Button
    lateinit var edt1: EditText
    lateinit var edt2: EditText

    var adminSQLite: AdminSQLite? = null
    var conector: SQLiteDatabase? = null
    var datos: ContentValues? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegister = findViewById<Button>(R.id.button_register_submit)
        btnRegresar = findViewById<Button>(R.id.button_secondary_action)
        edt1 = findViewById<EditText>(R.id.edit_text_username_register)
        edt2 = findViewById<EditText>(R.id.edit_text_password_register)

        btnRegresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
            val user = edt1.text.toString()
            val pass = edt2.text.toString()

            if (user.isNotBlank() && pass.isNotBlank()) {
                datos = ContentValues()
                datos?.put("User", user)
                datos?.put("Password", pass)

                guardarDatos(this@Registro, "CalificacionesDB", 1, datos)

            }
        }
    }

    private fun guardarDatos(contexto: Context, bd: String, version: Int, dataSet: ContentValues?): Long {
        adminSQLite = AdminSQLite(contexto, bd, version)
        conector = adminSQLite?.writableDatabase

        val result = conector?.insert("Usuario", null, dataSet) ?: -1L
        conector?.close()
        Toast.makeText(this, "Creado", Toast.LENGTH_SHORT).show()
        return result
    }
}
