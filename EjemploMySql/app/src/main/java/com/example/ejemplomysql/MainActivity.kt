package com.example.ejemplomysql

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class MainActivity : AppCompatActivity() {
    lateinit var edt1: EditText
    lateinit var edt2: EditText
    lateinit var edt3: EditText
    lateinit var edt4: EditText
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button

    var url: String = "http://192.168.15.135/Android/BDescuela/"
    lateinit var requestQueue: RequestQueue
    lateinit var stringRequest: StringRequest

    lateinit var JSONtodd: JSONObject
    lateinit var arregloJSON: JSONArray





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        edt1 = findViewById(R.id.edt1)
        edt2 = findViewById(R.id.edt2)
        edt3 = findViewById(R.id.edt3)
        edt4 = findViewById(R.id.edt4)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)



        btn1.setOnClickListener {
            guardar()
        }
        btn2.setOnClickListener {
            consultar(edt1.text.toString())
        }
        btn3.setOnClickListener {
            actualizar(edt1.text.toString(), edt2.text.toString(), edt3.text.toString(), edt4.text.toString())
        }
        btn4.setOnClickListener {
            eliminar(edt1.text.toString())
        }


    }
    private fun guardar(){
        requestQueue = Volley.newRequestQueue(this@MainActivity)

        stringRequest = object : StringRequest(Method.POST,"${url}guardar.php",
            Response.Listener{
                Toast.makeText(this@MainActivity, "si jalo", Toast.LENGTH_SHORT).show()
                edt1.setText("")
                edt2.setText("")
                edt3.setText("")
                edt4.setText("")

        },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Error al guardar: ${error.message}", Toast.LENGTH_SHORT).show()
        }){
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("matr", edt1.text.toString())
                parametros.put("nom", edt2.text.toString())
                parametros.put("carr", edt3.text.toString())
                parametros.put("tel", edt4.text.toString())
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

    private fun consultar(matr: String) {
        requestQueue = Volley.newRequestQueue(this@MainActivity)

        stringRequest = object : StringRequest(Method.POST,"${url}consultar.php",
            Response.Listener { response ->
                try {
                    JSONtodd = JSONObject(response)
                    arregloJSON = JSONtodd.getJSONArray("alumno")

                    if (arregloJSON.length() > 0) {
                        val objetoAlumno = arregloJSON.getJSONObject(0)
                        edt2.setText(objetoAlumno.getString("nombre"))
                        edt3.setText(objetoAlumno.getString("carrera"))

                        // Accedemos al objeto anidado "telefono" y luego a su propiedad "casa"
                        val objetoTelefono = objetoAlumno.getJSONObject("telefono")
                        edt4.setText(objetoTelefono.getString("casa"))
                    }
                } catch (e: Exception) {
                    // Si el servidor devuelve HTML con error (<br>), entrará aquí y evitará el crash
                    Toast.makeText(this@MainActivity, "Error en respuesta: " + e.message, Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Error de red: " + error.message, Toast.LENGTH_SHORT).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("matr", matr)
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

    private fun actualizar(matr: String, nom: String, carr: String, tel: String) {
        requestQueue = Volley.newRequestQueue(this@MainActivity)

        stringRequest = object : StringRequest(Method.POST,"${url}actualizar.php",
            Response.Listener{
                Toast.makeText(this@MainActivity, "si jalo", Toast.LENGTH_SHORT).show()
                edt1.setText("")
                edt2.setText("")
                edt3.setText("")
                edt4.setText("")

            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Error al guardar: ${error.message}", Toast.LENGTH_SHORT).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("matr", matr)
                parametros.put("nom", nom)
                parametros.put("carr", carr)
                parametros.put("tel", tel)
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

    private fun eliminar(matr: String) {
        requestQueue = Volley.newRequestQueue(this@MainActivity)

        stringRequest = object : StringRequest(Method.POST,"${url}eliminar.php",
            Response.Listener{
                Toast.makeText(this@MainActivity, "si jalo", Toast.LENGTH_SHORT).show()
                edt1.setText("")
                edt2.setText("")
                edt3.setText("")
                edt4.setText("")

            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, "Error al guardar: ${error.message}", Toast.LENGTH_SHORT).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("matr", matr)
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
