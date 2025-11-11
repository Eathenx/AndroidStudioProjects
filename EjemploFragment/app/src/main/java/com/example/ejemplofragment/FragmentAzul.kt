package com.example.ejemplofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class FragmentAzul : Fragment() {
    private var fotoselec: Int? = null
    private var imageviewazul: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fotoselec = it.getInt("foto")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageviewazul = view.findViewById(R.id.imageview1)

        if (fotoselec != null && fotoselec != 0) {
            imageviewazul?.setImageResource(fotoselec!!)
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_azul, container, false)
    }

    companion object {

    }
}