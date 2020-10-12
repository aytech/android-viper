package com.oleg.androidviper.data

import androidx.lifecycle.LiveData
import com.oleg.androidviper.data.entity.Movie

interface MovieRepository {

    fun getSavedMovies(): LiveData<List<Movie>>

    fun saveMovie(movie: Movie)

    fun deleteMovie(movie: Movie)

    fun searchMovies(query: String): LiveData<List<Movie>?>
}