package com.oleg.androidviper

import androidx.lifecycle.LiveData
import com.oleg.androidviper.data.entity.Movie

interface MainContract {

    interface View {
        fun deleteMoviesClicked()
        fun displayMovieList(movieList: List<Movie>)
        fun hideLoading()
        fun showLoading()
        fun showMessage(message: String)
    }

    interface Presenter {
        fun addMovie()
        fun deleteMovies(selectedMovies: HashSet<Movie>)
        fun onDestroy()
        fun onViewCreated()
    }

    interface Interactor {
        fun deleteMovie(movie: Movie)
        fun getAllMovies()
        fun loadMovieList(): LiveData<List<Movie>>
    }

    interface InteractorOutput {
        fun onQueryError()
        fun onQuerySuccess(data: List<Movie>)
    }
}