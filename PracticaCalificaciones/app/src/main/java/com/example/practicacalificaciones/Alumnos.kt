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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Alumnos : AppCompatActivity() {
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
        setContentView(R.layout.activity_alumnos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn1 = findViewById(R.id.Btn1)
        btn2 = findViewById(R.id.Btn2)
        btn3 = findViewById(R.id.Btn3)
        btn4 = findViewById(R.id.Btn4)
        edt1 = findViewById(R.id.Edit1)
        edt2 = findViewById(R.id.Edit2)
        edt3 = findViewById(R.id.Edit3)


        btn1.setOnClickListener {
            datos = ContentValues()
            datos?.put("matricula", edt1.text.toString())
            datos?.put("nombre", edt2.text.toString())
            datos?.put("carrera", edt3.text.toString())

            guardarDatos(this@Alumnos,"CalificacionesDB",1,datos)
            Toast.makeText(this, "Alumno guardado", Toast.LENGTH_SHORT).show()
        }
        btn2.setOnClickListener {
            var consultarDatos = consultarDatos(this@Alumnos, "CalificacionesDB", "select * from alumnos where matricula = '${edt1.text.toString()}'", 1)
            while (consultarDatos!!.moveToNext()) {
                edt2.setText(consultarDatos?.getString(1).toString())
                edt3.setText(consultarDatos?.getString(2).toString())
            }
            conector?.close()
//            crearAlertDialog()
        }
        btn3.setOnClickListener {
            var CadenaSQL = "delete from alumnos where matricula = '${edt1.text.toString()}' "
            eliminarDatos(this@Alumnos, "CalificacionesDB", CadenaSQL, 1)
        }

        btn4.setOnClickListener {
            var CadenaSQL = "update alumnos set nombre = '${edt2.text.toString()}', carrera = '${edt3.text.toString()}' where matricula = '${edt1.text.toString()}' "
            actualizarDatos(this@Alumnos, "CalificacionesDB", CadenaSQL, 1)
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

    private fun crearAlertDialog(){
        var builder = AlertDialog.Builder(this@Alumnos)
        var i: Int = 0
        var infoAlert: Array<String?>
        var datosbd = consultarDatos(this@Alumnos, "CalificacionesDB", "select * from alumnos", 1)

        infoAlert = arrayOfNulls<String>(datosbd!!.count)
        while (datosbd!!.moveToNext()){
            infoAlert[i] = fila?.getString(1).toString()
            i = i + 1
        }
        builder.setTitle("Alumnos Disponibles")
            .setItems(infoAlert) { dialog, which ->
                Toast.makeText(this@Alumnos, datosAlert[which].toString(), Toast.LENGTH_SHORT).show()

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