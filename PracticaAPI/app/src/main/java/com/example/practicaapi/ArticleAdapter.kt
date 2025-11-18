package com.example.practicaapi

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    var articles: List<Article> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var title: TextView = view.findViewById(R.id.item_title)
        private var description: TextView = view.findViewById(R.id.item_description)
        private var image: ImageView = view.findViewById(R.id.item_image)

        fun bind(article: Article){
            title.text = article.title
            description.text = article.description ?: "No description available"
            if (!article.urlToImage.isNullOrEmpty()) {
                Picasso.get().load(article.urlToImage).into(image)
            } else {
                image.setImageResource(R.drawable.ic_launcher_background)
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val dialogView = LayoutInflater.from(context).inflate(R.layout.detalles, null)
                val dialogImage = dialogView.findViewById<ImageView>(R.id.dialog_image)
                val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
                val dialogDescription = dialogView.findViewById<TextView>(R.id.dialog_description)

                dialogTitle.text = article.title
                dialogDescription.text = article.description ?: "No description available"

                if (!article.urlToImage.isNullOrEmpty()) {
                    Picasso.get().load(article.urlToImage).into(dialogImage)
                } else {
                    dialogImage.setImageResource(R.drawable.ic_launcher_background)
                }

                AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setPositiveButton("Close") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }
}
