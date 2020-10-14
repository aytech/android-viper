package com.oleg.androidviper.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.oleg.androidviper.contracts.MainContract
import com.oleg.androidviper.data.MovieRepositoryImpl
import com.oleg.androidviper.data.entity.Movie

class MainInteractor : MainContract.Interactor {

    private val movieList = MediatorLiveData<List<Movie>>()
    private val repository: MovieRepositoryImpl = MovieRepositoryImpl()

    init {
        getAllMovies()
    }

    override fun deleteMovie(movie: Movie) {
        repository.deleteMovie(movie)
    }

    override fun getAllMovies() {
        movieList.addSource(repository.getSavedMovies()) { movies -> movieList.postValue(movies) }
    }

    override fun loadMovieList(): LiveData<List<Movie>> = movieList
}