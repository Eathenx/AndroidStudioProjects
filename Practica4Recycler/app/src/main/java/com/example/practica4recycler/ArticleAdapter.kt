package com.example.practica4recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){
    // Lista de articulos a mostrar
    var articles = arrayListOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * Crea y devuelve un ViewHolder para un elemento de la lista.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.article_item,parent,false)
        return ArticleViewHolder(view)
    }

    /**
     * Vincula los datos de un articulo a un ViewHolder.
     */
    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        val article = articles[position]
        holder.vincular(article)
    }

    /**
     * Devuelve el numero total de elementos en la lista.
     */
    override fun getItemCount() = articles.size

    /**
     * ViewHolder para un elemento de articulo.
     */
    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView = view.findViewById(R.id.article_title)
        private var description: TextView = view.findViewById(R.id.article_description)
        private var image: ImageView = view.findViewById(R.id.article_image)

        /**
         * Vincula los datos de un articulo a las vistas del ViewHolder.
         */
        fun vincular(article: Article) {
            title.text = article.title
            description.text = article.description
            image.setImageURI(article.imageUri)
        }
    }
}