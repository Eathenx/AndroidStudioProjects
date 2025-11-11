package com.example.practica1fragmentimg

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class Grupo1 : Fragment() {

    private lateinit var layout1: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grupo1, container, false)
        layout1 = view.findViewById(R.id.linear1)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtiene la lista de URIs del Bundle
        val imageUris = arguments?.getStringArrayList("image_uris") ?: emptyList<String>()

        // Limpia las vistas anteriores para no duplicar imágenes
        layout1.removeAllViews()

        imageUris.forEach { uriString ->
            val imageView = ImageView(context).apply {
                // La imagen se ajustará a su tamaño original
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setImageURI(Uri.parse(uriString))
            }
            // Añade el ImageView al layout
            layout1.addView(imageView)
        }
    }
}
