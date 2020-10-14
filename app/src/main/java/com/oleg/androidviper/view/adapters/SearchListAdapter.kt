package com.oleg.androidviper.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidviper.R
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.data.net.RetrofitClient.Companion.TMDB_IMAGE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_search.view.*

class SearchListAdapter(private val movies: List<Movie>?, private val listener: (Movie?) -> Unit) :
    RecyclerView.Adapter<SearchListAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_search, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies?.let {
            holder.bind(movies[position], position)
        }
    }

    override fun getItemCount(): Int = movies?.size ?: 0

    inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, position: Int) = with(view) {
            searchTitleTextView.text = movie.title
            searchReleaseDateTextView.text = movie.releaseDate
            view.setOnClickListener { listener(movies?.get(position)) }
            if (movie.posterPath != null) {
                Picasso.get().load(TMDB_IMAGE_URL + movie.posterPath).into(searchImageView)
            } else {
                searchImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
        }
    }

}
