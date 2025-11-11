package com.example.ejemplosqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    var adminSQLite: AdminSQLite? = null
    var conector: SQLiteDatabase? = null
    var fila: Cursor? = null

    lateinit var spn1: Spinner
    lateinit var spn2: Spinner

    lateinit var adaptadorAlumno: ArrayAdapter<String>
    lateinit var adaptadorMateria: ArrayAdapter<String>

    var listaAlumnos = mutableListOf<Alumno>()
    var listaMaterias = mutableListOf<Materia>()

    lateinit var datosAlert: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spn1 = findViewById(R.id.spinner1)
        spn2 = findViewById(R.id.spinner2)

        adaptadorAlumno = ArrayAdapter<String>(this@MainActivity2,android.R.layout.simple_spinner_dropdown_item)
        adaptadorMateria = ArrayAdapter<String>(this@MainActivity2,android.R.layout.simple_spinner_dropdown_item)

        spn1.adapter = adaptadorAlumno
        spn2.adapter = adaptadorMateria

        llenarSpinnerAlumno(this@MainActivity2,"Escuela","select * from alumnos",1)
        llenarSpinnerMateria()

        spn1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                datosAlert = arrayOfNulls<String>(1)
                datosAlert[0] = listaAlumnos.get(position).nombre

                val builder = AlertDialog.Builder(this@MainActivity2)

                builder.setTitle("seleccionado")
                    .setItems(datosAlert) {dialog, pos ->

                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun llenarSpinnerAlumno(contexto: Context,bd: String,CadenaSQL: String,version: Int){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.readableDatabase

        fila = conector?.rawQuery(CadenaSQL, null)

        while (fila!!.moveToNext()){
            listaAlumnos.add(Alumno(fila?.getString(0).toString(), fila?.getString(1).toString(), fila?.getString(2).toString()))
            adaptadorAlumno.add(fila?.getString(0).toString())
        }
        conector?.close()
        fila?.close()
    }

    private fun llenarSpinnerMateria(){
        listaMaterias = Materia.datos
        for (i in 0 until listaMaterias.size){
            adaptadorMateria.add(listaMaterias.get(i).nombre)
        }
    }


}