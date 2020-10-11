package com.oleg.androidviper.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidviper.data.entity.Movie
import java.util.HashSet

class MovieListAdapter(private val movies: List<Movie>?) :
    RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class MovieHolder(private val view: View) : RecyclerView.ViewHolder(view) {}
}
