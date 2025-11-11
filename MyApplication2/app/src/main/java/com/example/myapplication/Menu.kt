package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu : AppCompatActivity() {
    lateinit var boton1: Button
    lateinit var boton2: Button
    lateinit var boton3: Button
    lateinit var boton4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boton1 = findViewById(R.id.button1)
        boton2 = findViewById(R.id.button2)
        boton3 = findViewById(R.id.button3)
        boton4 = findViewById(R.id.button4)

        boton1.setOnClickListener {
            val llamado = Intent(this@Menu, MainActivity::class.java)
            startActivity(llamado)
        }

        boton2.setOnClickListener {
            val llamado = Intent(this@Menu, MainActivity2::class.java)
            startActivity(llamado)
        }
        boton3.setOnClickListener {
            val llamado = Intent(this@Menu, MainActivity3::class.java)
            startActivity(llamado)
        }
        boton4.setOnClickListener {
            val llamado = Intent(this@Menu, Spinner::class.java)
            startActivity(llamado)
        }

    }
}