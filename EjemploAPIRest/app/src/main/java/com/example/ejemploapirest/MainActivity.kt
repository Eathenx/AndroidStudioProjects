package com.example.ejemploapirest

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var spn1: Spinner
    private lateinit var adaptador1: ArrayAdapter<Categoria>
    private lateinit var img1: ImageView
    private lateinit var txt2: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button

    private lateinit var requestQueue: RequestQueue
    private var listaArticulos = mutableListOf<Articles>()
    private var cont: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        spn1 = findViewById(R.id.spn1)
        img1 = findViewById(R.id.img1)
        txt2 = findViewById(R.id.txt1)
        btn1 = findViewById(R.id.btn1) // Botón Siguiente
        btn2 = findViewById(R.id.btn2) // Botón Anterior

        // Inicializar Volley RequestQueue una sola vez
        requestQueue = Volley.newRequestQueue(this)

        // Configurar Spinner
        adaptador1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Categoria.datos)
        spn1.adapter = adaptador1

        // Listener para el botón "Siguiente"
        btn1.setOnClickListener {
            if (listaArticulos.isNotEmpty()) {
                if (cont < listaArticulos.size - 1) {
                    cont++
                    actualizarNoticia()
                } else {
                    Toast.makeText(this, "Fin de las noticias", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Listener para el botón "Anterior"
        btn2.setOnClickListener {
            if (listaArticulos.isNotEmpty()) {
                if (cont > 0) {
                    cont--
                    actualizarNoticia()
                } else {
                    Toast.makeText(this, "Inicio de las noticias", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Listener para la selección de categoría en el Spinner
        spn1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = adaptador1.getItem(position)!!
                val url = "https://newsapi.org/v2/top-headlines?country=us&category=${categoriaSeleccionada.valor}&apiKey=f83ae0f6261f4e70a357051376572a9c"
                peticionNoticias(url)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No es necesario hacer nada aquí
            }
        }
    }

    private fun peticionNoticias(dirURL: String) {
        val stringRequest = object : StringRequest(
            Method.GET, dirURL,
            Response.Listener { response ->
                try {
                    val objetoJson = JSONObject(response)
                    val articulosJson = objetoJson.getJSONArray("articles")
                    val tipo = object : TypeToken<MutableList<Articles>>() {}.type
                    listaArticulos = Gson().fromJson(articulosJson.toString(), tipo)

                    if (listaArticulos.isNotEmpty()) {
                        // Al cambiar de categoría, mostrar la primera noticia
                        cont = 0
                        actualizarNoticia()
                    } else {
                        Toast.makeText(this, "No se encontraron noticias para esta categoría", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al cargar noticias: ${error.message}", Toast.LENGTH_LONG).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                // El header 'User-Agent' es necesario para evitar errores 403 en algunas APIs
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }

    // Función para actualizar la vista con la noticia actual
    private fun actualizarNoticia() {
        if (cont >= 0 && cont < listaArticulos.size) {
            val articulo = listaArticulos[cont]
            txt2.text = articulo.title
            // Usar una imagen de placeholder en caso de que la URL sea nula o vacía
            if (!articulo.urlToImage.isNullOrEmpty()) {
                Picasso.get().load(articulo.urlToImage).into(img1)
            } else {
                img1.setImageResource(R.drawable.ic_launcher_background) // Reemplaza con tu imagen por defecto
            }
        }
    }
}