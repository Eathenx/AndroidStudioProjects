package com.example.ejemplosqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.service.autofill.Dataset
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity() {
    lateinit var edt1: EditText
    lateinit var edt2: EditText
    lateinit var edt3: EditText
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    var adminSQLite: AdminSQLite? = null
    var conector: SQLiteDatabase? = null
    var datos: ContentValues? = null
    var fila: Cursor? = null
    lateinit var datosAlert: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        edt1 = findViewById(R.id.Edit1)
        edt2 = findViewById(R.id.Edit2)
        edt3 = findViewById(R.id.Edit3)
        btn1 = findViewById(R.id.Btn1)
        btn2 = findViewById(R.id.Btn2)
        btn3 = findViewById(R.id.Btn3)
        btn4 = findViewById(R.id.Btn4)
        btn5 = findViewById(R.id.Btn5)
        btn6 = findViewById(R.id.Btn6)


        btn1.setOnClickListener {
            datos = ContentValues()
            datos?.put("matricula", edt1.text.toString())
            datos?.put("nombre", edt2.text.toString())
            datos?.put("carrera", edt3.text.toString())

            guardarDatos(this@MainActivity,"Escuela",1,datos)
        }

        btn2.setOnClickListener {
            crearAlertDialog()
        }

        btn3.setOnClickListener {
            var CadenaSQL = "delete from alumnos where matricula = '${edt1.text.toString()}' "
            eliminarDatos(this@MainActivity, "Escuela", CadenaSQL, 1)
        }

        btn4.setOnClickListener {
            var CadenaSQL = "update alumnos set nombre = '${edt2.text.toString()}', carrera = '${edt3.text.toString()}' where matricula = '${edt1.text.toString()}' "
            actualizarDatos(this@MainActivity, "Escuela", CadenaSQL, 1)
        }

        btn5.setOnClickListener {
            var consultarDatos = consultarDatos(this@MainActivity, "Escuela", "select * from alumnos where matricula = '${edt1.text.toString()}'", 1)

            edt2.text.clear()
            edt3.text.clear()

            while (consultarDatos!!.moveToNext()) {
                edt2.setText(consultarDatos?.getString(1).toString())
                edt3.setText(consultarDatos?.getString(2).toString())
            }
            conector?.close()
        }

        btn6.setOnClickListener {
            var consultarDatos = consultarDatosClass(this@MainActivity, "Escuela", "select * from alumnos where matricula = '${edt1.text.toString()}'", 1)
            edt2.setText(consultarDatos[0].nombre)
            edt3.setText(consultarDatos[0].carrera)
        }

    }

    private fun guardarDatos(contexto: Context,bd:String,version: Int, dataSet: ContentValues?){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.writableDatabase

        conector?.insert("alumnos", null, dataSet)
        conector?.close()
    }

    private fun consultarDatos(contexto: Context, bd: String, CadenaSQL: String,version: Int): Cursor?{
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.readableDatabase

        fila = conector?.rawQuery(CadenaSQL, null)
        //datosAlert = arrayOfNulls<String>(fila!!.count)


        return fila
    }

    private fun consultarDatosClass(contexto: Context, bd: String, CadenaSQL: String,version: Int): MutableList<Alumno>{
        var listaAlumnos: MutableList<Alumno> = mutableListOf()
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.readableDatabase

        fila = conector?.rawQuery(CadenaSQL, null)
//        datosAlert = arrayOfNulls<String>(fila!!.count)

        while (fila!!.moveToNext()){
            listaAlumnos.add(Alumno(fila?.getString(0).toString(), fila?.getString(1).toString(), fila?.getString(2).toString()))

        }
        conector?.close()
        fila?.close()
        return listaAlumnos
    }

    private fun crearAlertDialog(){
        var builder = AlertDialog.Builder(this@MainActivity)
        var i: Int = 0
        var infoAlert: Array<String?>
        var datosbd = consultarDatos(this@MainActivity, "Escuela", "select * from alumnos", 1)

        infoAlert = arrayOfNulls<String>(datosbd!!.count)
        while (datosbd!!.moveToNext()){
            infoAlert[i] = fila?.getString(1).toString()
            i = i + 1
        }
        builder.setTitle("Alumnos Disponibles")
            .setItems(infoAlert) { dialog, which ->
                Toast.makeText(this@MainActivity, datosAlert[which].toString(), Toast.LENGTH_SHORT).show()

            }
        builder.show()
    }

    public fun actualizarDatos(contexto: Context, bd: String, CadenaSQL: String, version: Int){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.writableDatabase
        conector?.execSQL(CadenaSQL)

        conector?.close()
    }

    public fun eliminarDatos(contexto: Context, bd: String, CadenaSQL: String, version: Int){
        adminSQLite = AdminSQLite(contexto, bd,  version)
        conector = adminSQLite?.writableDatabase
        conector?.execSQL(CadenaSQL)

        conector?.close()
    }
}