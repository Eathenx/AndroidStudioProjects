package com.example.practica3cronometro

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import java.util.Timer
import java.util.TimerTask

class CronometroService : Service() {
    var handler: Handler? = null
    var timer: Timer? = null
    var tiempoEnSegundos: Int = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val hours = tiempoEnSegundos / 3600
                val minutes = (tiempoEnSegundos % 3600) / 60
                val seconds = tiempoEnSegundos % 60
                update_listener?.actualizarCronometro(hours, minutes, seconds)
            }
        }
    }

    //Detiene el temporizador
    override fun onDestroy() {
        super.onDestroy()
        pausar()
        instance = null
    }


    //Inicia el temporizador
    fun iniciar() {
        if (timer == null) {
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    tiempoEnSegundos++
                    // Envía un mensaje al handler para que actualice.
                    handler?.sendEmptyMessage(0)
                }
            }, 0, 1000) // Se ejecuta cada segundo.
        }
    }

    // Pausa el temporizador.
    fun pausar() {
        timer?.cancel()
        timer = null
    }

    // Reinicia el temporizador
    fun reiniciar() {
        pausar()
        tiempoEnSegundos = 0
        handler?.sendEmptyMessage(0) // Actualiza  a "00:00:00".
    }

    // Maneja el servicio y la actualización.
    companion object {

        private var instance: CronometroService? = null
        var update_listener: MainActivity? = null
        fun setUpdateListener(activity: MainActivity) {
            update_listener = activity
        }
        fun getInstance(): CronometroService? {
            return instance
        }
    }
}
