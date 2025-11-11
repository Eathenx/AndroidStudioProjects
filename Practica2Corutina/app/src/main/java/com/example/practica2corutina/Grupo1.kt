package com.example.practica2corutina

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Grupo1 : Fragment(), CoroutineScope {

    private lateinit var imageView: ImageView
    private var imageSlideshowJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grupo1, container, false)
        imageView = view.findViewById(R.id.image_view_grupo1)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUris = arguments?.getStringArrayList("image_uris")?.map { Uri.parse(it) } ?: emptyList()

        if (imageUris.isNotEmpty()) {
            startImageSlideshow(imageUris)
        }
    }
    private fun startImageSlideshow(imageUris: List<Uri>) {
        imageSlideshowJob?.cancel()
        imageSlideshowJob = launch(Dispatchers.Main) {
            var currentIndex = 0
            while (true) {
                imageView.setImageURI(imageUris[currentIndex])
                delay(2000)
                currentIndex = (currentIndex + 1) % imageUris.size
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageSlideshowJob?.cancel()
    }
}
