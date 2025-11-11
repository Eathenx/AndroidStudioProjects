package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Spinner : AppCompatActivity() {
    lateinit var img1: ImageView
    lateinit var spinner1: Spinner
    lateinit var adaptador: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spinner)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        img1 = findViewById(R.id.ImageView1)
        spinner1 = findViewById(R.id.spinner1)
        adaptador =
            ArrayAdapter<String>(this@Spinner, android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adaptador

        adaptador.add("gato")
        adaptador.add("perro")
        adaptador.add("caleus")
        adaptador.add("pinguino")

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selection = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@Spinner, "$position", Toast.LENGTH_SHORT).show()
                when (selection) {
                    "gato" -> img1.setImageResource(R.mipmap.gaming)
                    "perro" -> img1.setImageResource(R.drawable.caleus)
                    "caleus" -> img1.setImageResource(R.drawable.sticker_ppg_08_dan_heng__3f_imbibitor_lunae_01)
                    "pinguino" -> img1.setImageResource(R.drawable.pinguino)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}

