package com.example.practicacalificaciones

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Materias : AppCompatActivity() {
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var edt1: EditText
    lateinit var edt2: EditText
    lateinit var edt3: EditText


    var adminSQLite: AdminSQLite? = null
    var conector: SQLiteDatabase? = null
    var datos: ContentValues? = null
    var fila: Cursor? = null
    lateinit var datosAlert: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_materias)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn1 = findViewById(R.id.Btn1)
        edt1 = findViewById(R.id.Edit1)
        edt2 = findViewById(R.id.Edit2)
        edt3 = findViewById(R.id.Edit3)

        btn1.setOnClickListener {
            datos = ContentValues()
            datos?.put("codigoM", edt1.text.toString())
            datos?.put("nombre", edt2.text.toString())
            datos?.put("creditos", edt3.text.toString())

            guardarDatos(this@Materias,"CalificacionesDB",1,datos)
            Toast.makeText(this, "Materia guardada", Toast.LENGTH_SHORT).show()
        }

    }

    private fun guardarDatos(contexto: Context,bd:String,version: Int, dataSet: ContentValues?){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.writableDatabase

        conector?.insert("materia", null, dataSet)
        conector?.close()
    }
}