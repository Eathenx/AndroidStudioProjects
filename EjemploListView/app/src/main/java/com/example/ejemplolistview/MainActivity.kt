package com.example.ejemplolistview

import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var text1: EditText
    lateinit var button1: Button
    lateinit var listview1 : ListView
    lateinit var adaptado: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text1 = findViewById(R.id.Text1)
        button1 = findViewById(R.id.button1)
        listview1 = findViewById(R.id.listview1)
        adaptado = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_multiple_choice)

        listview1.adapter = adaptado

        button1.setOnClickListener {
            adaptado.add(text1.text.toString())
            adaptado.notifyDataSetChanged()
        }
    }
}