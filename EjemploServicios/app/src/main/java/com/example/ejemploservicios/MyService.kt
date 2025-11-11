package com.example.ejemploservicios

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import java.util.Timer
import java.util.TimerTask

class MyService: Service() {

    var handler1: Handler? = null
    var tempo1: Timer = Timer()
    var intervalo_actualizacion: Long = 100
    var cont1: Int = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        iniciarconteo()
        handler1 = object: Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                update_listener?.actualizar(cont1)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        detenerconteo()
    }

    private fun iniciarconteo(){
        tempo1.schedule(object: TimerTask(){
            override fun run() {
                cont1++
                handler1?.sendEmptyMessage(0)
            }
        },0,intervalo_actualizacion)

    }

    private fun detenerconteo(){
        if(tempo1 != null){
            tempo1.cancel()
        }

    }
    
    companion object{
        var update_listener: MainActivity? = null

        fun setUpdateListener(pServicio: MainActivity){
            update_listener = pServicio
        }
    }

}