package com.example.ejemploservicios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var txt1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        txt1 = findViewById(R.id.textview1)


        btn1.setOnClickListener {
            iniciar()
        }

        btn2.setOnClickListener {
            detener()
        }

        MyService.setUpdateListener(this@MainActivity)
    }

    fun iniciar(){
        var servicio = Intent(this@MainActivity, MyService::class.java)
        startService(servicio)
    }

    fun detener(){
        var servicio = Intent(this@MainActivity, MyService::class.java)
        stopService(servicio)
    }

    fun actualizar(parContador: Int){
        txt1.text = parContador.toString()
    }
}