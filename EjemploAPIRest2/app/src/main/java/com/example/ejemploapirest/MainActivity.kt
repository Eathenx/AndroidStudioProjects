package com.example.ejemploapirest

import android.os.Bundle
import android.view.PixelCopy
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var sp1: Spinner
    lateinit var txt1: TextView
    lateinit var adaptador: ArrayAdapter<Categoria>
    lateinit var requestQueue: RequestQueue
    lateinit var stringRequest: StringRequest
    lateinit var objetoJSON: JSONObject
    lateinit var articulosJson: JSONArray
    lateinit var Url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sp1 = findViewById(R.id.spn1)
        txt1 = findViewById(R.id.txt1)

        adaptador = ArrayAdapter<Categoria>(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,
            Categoria.datos)
        sp1.adapter = adaptador

        sp1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                Url = "http://192.168.8.10/Android/ejemploJson.php"

                requestQueue = Volley.newRequestQueue(this@MainActivity)

                stringRequest = object : StringRequest(Method.GET,Url,
                    Response.Listener{
                        objetoJSON = JSONObject(it)
                        articulosJson = objetoJSON.getJSONArray("articles")

                        var listaArticulos = mutableListOf<Articles>()



                        txt1.text = ""
                        for (i in 0 until articulosJson.length()){
                            //del objetoJson al textview
                            //txt1.append("${articulosJson.getJSONObject(i).getString("author")} , ${articulosJson.getJSONObject(i).getString("title")} \n")

                            //de una lista mutable al textview
                            val autor = articulosJson.getJSONObject(i).getString("author")
                            val titulo = articulosJson.getJSONObject(i).getString("title")
                            listaArticulos.add(Articles(autor,titulo))
                            txt1.append("${listaArticulos.get(i).title}, ${listaArticulos.get(i).Author} \n")



                        }
                        //directo a la mutable sin for
                        val tipo = object : TypeToken<List<Articles>>(){}.type

                        listaArticulos = Gson().fromJson(articulosJson.toString(),tipo)





//                        txt1.text = objetoJSON.getJSONArray("articles").toString()

                },
                    Response.ErrorListener{

                    }){
                    override fun getParams(): Map<String?,String?>?{
                        return super.getParams()
                    }
                    override fun getHeaders(): MutableMap<String,String>{
                        val headers: HashMap<String, String> = HashMap()
                        headers["user-agent"] = "Mozilla/5.0"
                        return headers
                    }
                }
                requestQueue.add(stringRequest)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}