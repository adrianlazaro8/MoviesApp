package com.adrianlazaro.moviesapp.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.common.basicDiffUtil
import com.adrianlazaro.moviesapp.common.inflate
import com.adrianlazaro.moviesapp.common.loadUrl
import kotlinx.android.synthetic.main.item_video.view.*

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_video, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.iv_movie.loadUrl(movie.posterPath)
            itemView.tv_movie.text = movie.title
        }
    }
}