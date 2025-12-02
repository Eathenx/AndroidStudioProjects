package com.example.ejemplomysql

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {
    lateinit var spn1: Spinner
    lateinit var spn2: Spinner
    lateinit var adapator: ArrayAdapter<String>
    lateinit var adapator2: ArrayAdapter<String>

    var url: String = "http://192.168.137.12/Android/BDescuela/"
    lateinit var requestQueue: RequestQueue
    lateinit var stringRequest: StringRequest

    lateinit var JSONtodd: JSONObject
    lateinit var arregloJSON: JSONArray
    lateinit var arregloJSON2: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        spn1 = findViewById(R.id.spn1)
        spn2 = findViewById(R.id.spn2)

        adapator = ArrayAdapter(this@MainActivity2, android.R.layout.simple_spinner_item)
        adapator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        adapator2 = ArrayAdapter(this@MainActivity2, android.R.layout.simple_spinner_item)
        adapator2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spn1.adapter = adapator
        spn2.adapter = adapator2 // Corrección: Asignar el adaptador al segundo spinner

        llenarSpinner()

        spn1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Verificamos si arregloJSON está inicializado para evitar cierres inesperados
                if (::arregloJSON.isInitialized && position < arregloJSON.length()) {
                    try {
                        Toast.makeText(this@MainActivity2, arregloJSON.getJSONObject(position).getString("nombre").toString(), Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    
    private fun llenarSpinner(){
        requestQueue = Volley.newRequestQueue(this@MainActivity2)

        stringRequest = object : StringRequest(Method.GET,"${url}llenarSpinnerA.php",
            Response.Listener { response ->
                try {
                    JSONtodd = JSONObject(response)

                    // Asegúrate de que estas claves coincidan exactamente con tu PHP
                    arregloJSON = JSONtodd.getJSONArray("alumnos")
                    arregloJSON2 = JSONtodd.getJSONArray("productos")

                    adapator.clear()
                    var i = 0
                    while (i < arregloJSON.length()){
                        adapator.add(arregloJSON.getJSONObject(i).getString("matricula").toString())
                        i++
                    }
                    adapator.notifyDataSetChanged()

                    adapator2.clear()
                    var j = 0
                    while (j < arregloJSON2.length()){
                        // Corrección: Usar adapator2 para el segundo array
                        adapator2.add(arregloJSON2.getJSONObject(j).getString("ProductName").toString()) 
                        j++
                    }
                    adapator2.notifyDataSetChanged()
                    
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity2, "Error procesando datos: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity2, "Error de red: ${error.message}", Toast.LENGTH_LONG).show()
            }){

            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                return parametros
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        requestQueue.add(stringRequest)
    }
}