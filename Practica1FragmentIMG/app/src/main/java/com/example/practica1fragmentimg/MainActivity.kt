package com.example.practica1fragmentimg

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Declaración de las vistas y listas
    private lateinit var img1: ImageView
    private lateinit var radioGroup1: RadioGroup
    private lateinit var btn1: Button

    private val uris1 = ArrayList<String>()
    private val uris2 = ArrayList<String>()

    //Seleccionar una imagen de la galería
    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                img1.setImageURI(imageUri)
                //Añadimos la URI a la lista
                when (radioGroup1.checkedRadioButtonId) {
                    R.id.radioButton1 -> {
                        uris1.add(imageUri.toString())
                    }
                    R.id.radioButton2 -> {
                        uris2.add(imageUri.toString())
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicialización de las vistas
        img1 = findViewById(R.id.imageView)
        radioGroup1 = findViewById(R.id.radioGroup)
        btn1 = findViewById(R.id.button)

        //Abrir la galería al hacer clic en la imagen
        img1.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        // Listener para mostrar el fragmento correspondiente al hacer clic en el botón
        btn1.setOnClickListener {
            val selectedRadioButtonId = radioGroup1.checkedRadioButtonId
            when (selectedRadioButtonId) {
                R.id.radioButton1 -> {
                    // Creamos el fragmento y le pasamos los datos con un Bundle
                    val fragment = Grupo1()
                    val bundle = Bundle()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()

                    bundle.putStringArrayList("image_uris", uris1)
                    fragment.arguments = bundle

                    fragmentTransaction.replace(R.id.fragment_container, fragment, "GRUPO_1")
                    fragmentTransaction.commit()
                }
                R.id.radioButton2 -> {
                    // Creamos el fragmento y le pasamos los datos con un Bundle
                    val fragment = Grupo2()
                    val bundle = Bundle()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()

                    bundle.putStringArrayList("image_uris", uris2)
                    fragment.arguments = bundle

                    fragmentTransaction.replace(R.id.fragment_container, fragment, "GRUPO_2")
                    fragmentTransaction.commit()
                }
                else -> {
                    Toast.makeText(this, "Por favor, elige un grupo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
