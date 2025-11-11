package com.example.ejemploalertdialog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    lateinit var button1: Button
    lateinit var button2: Button
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

        editText = findViewById(R.id.editText)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)

        button1.setOnClickListener {
            datosAlert = arrayOfNulls<String>(6)

            datosAlert[0] = "Ingenieria en Informatica"
            datosAlert[1] = "Ingenieria en Mecatronica"
            datosAlert[2] = "Ingenieria en Industria Alimentaria"
            datosAlert[3] = "Ingenieria en Inteligencia Artificial"
            datosAlert[4] = "Ingenieria en Industrial"
            datosAlert[5] = "Ingenieria en Administracion"

            var builder = AlertDialog.Builder(this@MainActivity)

            builder.setTitle("Carreras Disponibles")
                .setItems(datosAlert) { dialog, which ->
                    Toast.makeText(this@MainActivity, datosAlert[which].toString(), Toast.LENGTH_SHORT).show()

                }
            builder.show()

        }

        button2.setOnClickListener {

        }
    }
}