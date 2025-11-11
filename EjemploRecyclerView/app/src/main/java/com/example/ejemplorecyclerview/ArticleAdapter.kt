package com.example.ejemplorecyclerview


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    var articles = arrayListOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.article_item,parent,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        val article = articles[position]
        holder.vincular(article)
    }

    override fun getItemCount() = articles.size

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var title: TextView = view.findViewById(R.id.article_title)
        private var description: TextView = view.findViewById(R.id.article_description)
        private var image: ImageView = view.findViewById(R.id.article_image)

        fun vincular(article: Article){
            title.text = article.title
            description.text = article.description
            image.setImageResource(article.imageRes)
        }
    }
}