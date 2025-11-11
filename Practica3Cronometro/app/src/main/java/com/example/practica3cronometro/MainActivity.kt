package com.example.practica3cronometro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ListView
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var tvTiempo: TextView
    private lateinit var btnIniciar: Button
    private lateinit var btnPausar: Button
    private lateinit var btnReiniciar: Button
    private lateinit var btnVuelta: Button
    private lateinit var lvVueltas: ListView
    private lateinit var lapsAdapter: ArrayAdapter<String>
    private val lapsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTiempo = findViewById(R.id.tvTiempo)
        btnIniciar = findViewById(R.id.btnIniciar)
        btnPausar = findViewById(R.id.btnPausar)
        btnReiniciar = findViewById(R.id.btnReiniciar)
        btnVuelta = findViewById(R.id.btnVuelta)
        lvVueltas = findViewById(R.id.lvVueltas)

        tvTiempo.text = "00:00:00"

        lapsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lapsList)
        lvVueltas.adapter = lapsAdapter

        //Inicio del servicio y comunicación ---
        val serviceIntent = Intent(this, CronometroService::class.java)
        startService(serviceIntent)
        CronometroService.setUpdateListener(this)


        //Botón para iniciar el cronómetro.
        btnIniciar.setOnClickListener {
            CronometroService.getInstance()?.iniciar()
        }

        //Botón para pausar el cronómetro.
        btnPausar.setOnClickListener {
            CronometroService.getInstance()?.pausar()
        }

        //Botón para reiniciar el cronómetro
        btnReiniciar.setOnClickListener {
            CronometroService.getInstance()?.reiniciar()
            lapsList.clear()
            lapsAdapter.notifyDataSetChanged()
        }

        // Botón para registrar una vuelta.
        btnVuelta.setOnClickListener {
             lapsList.add(tvTiempo.text.toString())
             lapsAdapter.notifyDataSetChanged()
        }
    }

    // Actualiza el texto del cronómetro en la pantalla.
    fun actualizarCronometro(hours: Int, minutes: Int, seconds: Int) {
        tvTiempo.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, CronometroService::class.java)
        stopService(intent)
    }
}
