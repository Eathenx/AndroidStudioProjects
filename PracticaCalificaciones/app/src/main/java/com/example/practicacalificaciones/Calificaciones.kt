package com.example.practicacalificaciones

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Calificaciones : AppCompatActivity() {
    lateinit var spn1: Spinner
    lateinit var spn2: Spinner
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var U1: EditText
    lateinit var U2: EditText
    lateinit var U3: EditText
    lateinit var U4: EditText

    lateinit var adaptadorAlumno: ArrayAdapter<String>
    lateinit var adaptadorMateria: ArrayAdapter<String>

    var listaAlumnos = mutableListOf<Alumno>()
    var listaMaterias = mutableListOf<Materia>()

    var adminSQLite: AdminSQLite? = null
    var conector: SQLiteDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calificaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spn1 = findViewById(R.id.spinner_alumno)
        spn2 = findViewById(R.id.spinner_materia)
        btn1 = findViewById(R.id.Btn1)
        btn2 = findViewById(R.id.Btn2)
        U1 = findViewById(R.id.edit_text_unidad1)
        U2 = findViewById(R.id.edit_text_unidad2)
        U3 = findViewById(R.id.edit_text_unidad3)
        U4 = findViewById(R.id.edit_text_unidad4)

        adaptadorAlumno = ArrayAdapter<String>(this@Calificaciones,android.R.layout.simple_spinner_dropdown_item)
        adaptadorMateria = ArrayAdapter<String>(this@Calificaciones,android.R.layout.simple_spinner_dropdown_item)

        spn1.adapter = adaptadorAlumno
        spn2.adapter = adaptadorMateria

        llenarSpinnerAlumno(this@Calificaciones,"CalificacionesDB","select * from alumnos",1)
        llenarSpinnerMateria(this@Calificaciones,"CalificacionesDB","select * from materia",1)

        // Listeners vacíos para que no interfieran
        spn1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
        spn2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        btn1.setOnClickListener {
            val selectedMateriaName = spn2.selectedItem.toString()
            val materia = listaMaterias.find { it.nombre == selectedMateriaName }

            if (materia == null) {
                Toast.makeText(this, "Error: Materia no válida.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val codigoMateria = materia.codigoM

            val datos = ContentValues()
            datos.put("matricula", spn1.selectedItem.toString())
            datos.put("codigoM", codigoMateria)
            datos.put("U1", U1.text.toString())
            datos.put("U2", U2.text.toString())
            datos.put("U3", U3.text.toString())
            datos.put("U4", U4.text.toString())

            guardarDatos(this@Calificaciones,"CalificacionesDB",1,datos)
            Toast.makeText(this, "Calificaciones guardadas", Toast.LENGTH_SHORT).show()
        }

        btn2.setOnClickListener {
            crearAlertDialog()
        }
    }

    private fun llenarSpinnerAlumno(contexto: Context,bd: String,CadenaSQL: String,version: Int){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.readableDatabase
        val fila = conector?.rawQuery(CadenaSQL, null)

        fila?.use {
            while (it.moveToNext()){
                listaAlumnos.add(Alumno(it.getString(0), it.getString(1), it.getString(2)))
                adaptadorAlumno.add(it.getString(0))
            }
        }
        conector?.close()
    }

    private fun llenarSpinnerMateria(contexto: Context,bd: String,CadenaSQL: String,version: Int){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.readableDatabase
        val fila = conector?.rawQuery(CadenaSQL, null)

        fila?.use {
            while (it.moveToNext()){
                listaMaterias.add(Materia(it.getString(0), it.getString(1), it.getString(2)))
                adaptadorMateria.add(it.getString(1))
            }
        }
        conector?.close()
    }

    private fun guardarDatos(contexto: Context,bd:String,version: Int, dataSet: ContentValues?){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.writableDatabase
        // Usamos insertWithOnConflict para reemplazar si ya existe una calificación para ese alumno/materia
        conector?.insertWithOnConflict("calificaciones", null, dataSet, SQLiteDatabase.CONFLICT_REPLACE)
        conector?.close()
    }

    private fun consultarDatos(contexto: Context, bd: String, CadenaSQL: String,version: Int): Cursor?{
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.readableDatabase
        return conector?.rawQuery(CadenaSQL, null)
    }

    private fun crearAlertDialog() {
        val selectedMatricula = spn1.selectedItem.toString()
        val selectedMateriaName = spn2.selectedItem.toString()

        val alumno = listaAlumnos.find { it.matricula == selectedMatricula }
        val materia = listaMaterias.find { it.nombre == selectedMateriaName }

        if (alumno == null || materia == null) {
            Toast.makeText(this, "Error: Alumno o materia no encontrado.", Toast.LENGTH_SHORT).show()
            return
        }

        val nombreAlumno = alumno.nombre
        val nombreMateria = materia.nombre
        val codigoMateria = materia.codigoM

        val queryString = "SELECT U1, U2, U3, U4 FROM calificaciones WHERE matricula = '$selectedMatricula' AND codigoM = '$codigoMateria'"
        val datosbd = consultarDatos(this@Calificaciones, "CalificacionesDB", queryString, 1)

        val infoAlert: Array<String>

        datosbd.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                val u1 = cursor.getString(0) ?: "N/A"
                val u2 = cursor.getString(1) ?: "N/A"
                val u3 = cursor.getString(2) ?: "N/A"
                val u4 = cursor.getString(3) ?: "N/A"

                infoAlert = arrayOf(
                    "Unidad 1: $u1",
                    "Unidad 2: $u2",
                    "Unidad 3: $u3",
                    "Unidad 4: $u4"
                )
            } else {
                infoAlert = arrayOf("No se encontraron calificaciones para este alumno en esta materia.")
            }
        }

        val dialogTitle = "Calificaciones de $nombreMateria del alumn@ $nombreAlumno"
        val builder = AlertDialog.Builder(this@Calificaciones)
        builder.setTitle(dialogTitle)
            .setItems(infoAlert) { dialog, which ->
                Toast.makeText(this@Calificaciones, infoAlert[which], Toast.LENGTH_SHORT).show()
            }
        builder.show()
    }
}
