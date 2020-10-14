package com.oleg.androidviper.interactor

import androidx.lifecycle.LiveData
import com.oleg.androidviper.contracts.SearchContract
import com.oleg.androidviper.data.MovieRepositoryImpl
import com.oleg.androidviper.data.entity.Movie

class SearchInteractor : SearchContract.Interactor {

    private val repository: MovieRepositoryImpl = MovieRepositoryImpl()

    override fun addMovie(movie: Movie?) {
        movie?.let {
            repository.saveMovie(movie)
        }
    }

    override fun searchMovies(title: String): LiveData<List<Movie>?> =
        repository.searchMovies(title)
}