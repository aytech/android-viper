package com.oleg.androidviper.contracts

import com.oleg.androidviper.data.entity.Movie

interface AddContract {

    interface View {
        fun showMessage(message: String)
        fun searchMovieClicked(view: android.view.View)
        fun addMovieClicked(view: android.view.View)
    }

    interface Presenter {
        fun addMovies(title: String, year: String)
        fun searchMovies(title: String)
        fun onDestroy()
    }

    interface Interactor {
        fun addMovie(movie: Movie)
    }
}