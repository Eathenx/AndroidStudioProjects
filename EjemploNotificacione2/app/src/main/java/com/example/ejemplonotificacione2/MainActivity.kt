package com.example.ejemplonotificacione2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var edt1: EditText
    lateinit var btn1: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edt1 = findViewById(R.id.edittext)
        btn1 = findViewById(R.id.boton)

        crearnotificacion()


        btn1.setOnClickListener {
            generarNotificacion()

        }


    }

    private fun crearnotificacion(){

        val name = "Mi primera notificacion"
        val channelId = "basic channel"
        val text = "Esta es mi primera notificacion de android"
        val importance = NotificationManager.IMPORTANCE_DEFAULT


        val channel = NotificationChannel(channelId, name, importance).apply {
            description = text
        }

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

    }

    private fun generarNotificacion(){
        val channelId = "basic channel"
        val notifIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)

        val imagen = BitmapFactory.decodeResource(resources, R.drawable.gaming)

        val intent = Intent(this@MainActivity, MainActivity2::class.java)
        intent.putExtra("imagen", R.drawable.gaming)

        val pendingIntent = PendingIntent.getActivity(this@MainActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)



        val notification = NotificationCompat.Builder(this@MainActivity, channelId)
            .setLargeIcon(notifIcon)
            .setSmallIcon(R.drawable.gaming)
            .setContentTitle("SI")
            .setContentText(edt1.text.toString())
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(imagen))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSubText("Sitio WEb")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(this@MainActivity )){
            if(ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
                return
            }

            notificationId++
            notify(notificationId, notification)

        }


    }

    companion object{
        var notificationId: Int = 0
    }

}