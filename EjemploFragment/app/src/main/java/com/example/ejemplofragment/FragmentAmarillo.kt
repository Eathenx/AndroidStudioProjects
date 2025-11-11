package com.example.ejemplofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner

class FragmentAmarillo : Fragment() {
    private var fotoselec: Int? = null
    private var imageviewamarillo: ImageView? = null

    lateinit var spinner: Spinner

    lateinit var adaptador: ArrayAdapter<String>

    var lista: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fotoselec = it.getInt("foto")
            lista = arrayListOf()
            lista?.addAll( it.getStringArrayList("lista")!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageviewamarillo = view.findViewById(R.id.imageview1)

        if (fotoselec != null && fotoselec != 0) {
            imageviewamarillo?.setImageResource(fotoselec!!)
        }

        spinner =view.findViewById(R.id.spinner)
        adaptador = ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_dropdown_item)

        adaptador.addAll(lista)
        spinner.adapter = adaptador
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amarillo, container, false)
    }

    companion object {

    }
}