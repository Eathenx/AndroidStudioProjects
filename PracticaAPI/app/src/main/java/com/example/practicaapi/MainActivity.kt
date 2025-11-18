package com.example.practicaapi

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var spn1: Spinner
    private lateinit var adaptadorCategorias: ArrayAdapter<Categoria>
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        spn1 = findViewById(R.id.spinner1)
        recyclerView = findViewById(R.id.recycler1)

        // Inicializar Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        // Configurar RecyclerView
        articleAdapter = ArticleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter

        // Configurar Spinner
        adaptadorCategorias = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Categoria.datos)
        spn1.adapter = adaptadorCategorias

        // Listener para la selección de categoría en el Spinner
        spn1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = adaptadorCategorias.getItem(position)!!
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
                    val tipo = object : TypeToken<List<Article>>() {}.type
                    val articles: List<Article> = Gson().fromJson(articulosJson.toString(), tipo)

                    articleAdapter.articles = articles

                    if (articles.isEmpty()) {
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
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }
}
