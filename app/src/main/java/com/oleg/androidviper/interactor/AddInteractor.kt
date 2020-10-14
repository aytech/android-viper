package com.oleg.androidviper.interactor

import com.oleg.androidviper.contracts.AddContract
import com.oleg.androidviper.data.MovieRepositoryImpl
import com.oleg.androidviper.data.entity.Movie

class AddInteractor : AddContract.Interactor {

    private val repository: MovieRepositoryImpl = MovieRepositoryImpl()

    override fun addMovie(movie: Movie) = repository.saveMovie(movie)

}
