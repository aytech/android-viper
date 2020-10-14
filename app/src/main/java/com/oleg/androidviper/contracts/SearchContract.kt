package com.oleg.androidviper.contracts

import androidx.lifecycle.LiveData
import com.oleg.androidviper.data.entity.Movie

interface SearchContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMessage(message: String)
        fun showRetryMessage()
        fun displayMovieList(movies: List<Movie>)
        fun displayConfirmation(movie: Movie?)
    }

    interface Presenter {
        fun searchMovies(title: String)
        fun addMovieClicked(movie: Movie?)
        fun movieClicked(movie: Movie?)
        fun onDestroy()
    }

    interface Interactor {
        fun addMovie(movie: Movie?)
        fun searchMovies(title: String): LiveData<List<Movie>?>
    }

    interface InteractorOutput {
        fun onQueryEmpty()
        fun onQueryError()
        fun onQuerySuccess(movies: List<Movie>)
    }
}