package com.oleg.androidviper.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidviper.R
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.data.net.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_main.view.*
import java.util.HashSet

class MovieListAdapter(private val movies: List<Movie>?) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_main, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies?.let { holder.bind(movies[position]) }
    }

    override fun getItemCount(): Int = movies?.size ?: 0

    inner class MovieHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) = with(view) {
            movieTitleTextView.text = movie.title
            movieReleaseDateTextView.text = movie.releaseDate
            checkbox.isChecked = movie.watched
            if (movie.posterPath != null) {
                Picasso.get().load(RetrofitClient.TMDB_IMAGE_URL + movie.posterPath)
                    .into(movieImageView)
            } else {
                movieImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_local_movies_gray,
                        null
                    )
                )
            }
            checkbox.setOnCheckedChangeListener { checkbox, isChecked ->
                if (!selectedMovies.contains(movie) && isChecked) {
                    checkbox.isChecked = true
                    selectedMovies.add(movie)
                } else {
                    checkbox.isChecked = false
                    selectedMovies.remove(movie)
                }
            }
        }
    }
}
