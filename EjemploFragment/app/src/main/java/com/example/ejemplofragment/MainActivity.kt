package com.example.ejemplofragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var btnazul: Button
    lateinit var btnamarillo: Button
    lateinit var contenedor: FrameLayout

    lateinit var radiobtn1: RadioButton
    lateinit var radiobtn2: RadioButton
    lateinit var radiobtn3: RadioButton

    lateinit var datos: Bundle

    lateinit var arrayList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnazul = findViewById(R.id.button1)
        btnamarillo = findViewById(R.id.button2)
        contenedor = findViewById(R.id.contenedorFrag)

        radiobtn1 = findViewById(R.id.radio1)
        radiobtn2 = findViewById(R.id.radio2)
        radiobtn3 = findViewById(R.id.radio3)

        datos = Bundle()

        btnazul.setOnClickListener {
            cargarFragmento(FragmentAzul(),verifestado())
        }

        btnamarillo.setOnClickListener {
            cargarFragmento(FragmentAmarillo(),verifestado())
        }
    }
    private fun cargarFragmento(fragmento: Fragment, selec: Int){
        val fragmentTransaction = supportFragmentManager.beginTransaction()


        datos.putInt("foto",selec)
        fragmento.arguments = datos

        arrayList = arrayListOf()

        arrayList.add("1")
        arrayList.add("2")
        arrayList.add("3")
        arrayList.add("4")
        arrayList.add("5")

        datos.putStringArrayList("lista",arrayList)

        fragmentTransaction.replace(R.id.contenedorFrag,fragmento)
        fragmentTransaction.commit()
    }

    private fun verifestado(): Int{
        return when{
            radiobtn1.isChecked->R.drawable.yo
            radiobtn2.isChecked->R.drawable.sticker_ppg_08_dan_heng__3f_imbibitor_lunae_01
            radiobtn3.isChecked->R.drawable.vibrant_harriers_official_wallpaper_genshin_1
            else->R.drawable.ezgif_1f4c786ef308f6
        }


        return 0
    }
}