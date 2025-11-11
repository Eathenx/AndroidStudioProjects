package com.example.practicacalificaciones

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu : AppCompatActivity() {
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn1 = findViewById<Button>(R.id.button_materias)
        btn2 = findViewById<Button>(R.id.button_alumnos)
        btn3 = findViewById<Button>(R.id.button_calificaciones)

        btn1.setOnClickListener {
            val intent = android.content.Intent(this, Materias::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = android.content.Intent(this, Alumnos::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = android.content.Intent(this, Calificaciones::class.java)
            startActivity(intent)
        }
    }
}