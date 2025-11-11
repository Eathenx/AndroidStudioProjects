package com.example.practica4recycler

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    // Vistas y variables de la actividad
    lateinit var btn1: Button
    lateinit var txt1: EditText
    lateinit var txt2: EditText
    lateinit var recycler: RecyclerView
    lateinit var adaptador: ArticleAdapter
    private lateinit var imageView: ImageView
    private val imageUris = ArrayList<Uri>()

    // Lanzador para seleccionar una imagen de la galeria
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                imageUris.add(it)
                imageView.setImageURI(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializacion de vistas
        btn1 = findViewById(R.id.button1)
        txt1 = findViewById(R.id.editTextTitulo)
        txt2 = findViewById(R.id.editTextDescripcion)
        imageView = findViewById(R.id.imageView)
        recycler = findViewById(R.id.recyclerview1)
        adaptador = ArticleAdapter()
        recycler.adapter = adaptador

        // Listener para seleccionar una imagen al hacer clic en el ImageView
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        // Listener para agregar un nuevo articulo al hacer clic en el boton
        btn1.setOnClickListener {
            if (imageUris.isNotEmpty()) {
                val article = Article(
                    txt1.text.toString(),
                    txt2.text.toString(),
                    imageUris.last()
                )
                adaptador.articles.add(article)
                adaptador.notifyDataSetChanged()
            }
        }
    }
}
