package com.oleg.androidviper.presenter

import com.nhaarman.mockito_kotlin.verify
import com.oleg.androidviper.contracts.AddContract
import com.oleg.androidviper.data.entity.Movie
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Cicerone

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTest {

    private lateinit var presenter: AddPresenter
    @Mock
    private lateinit var view: AddContract.View
    @Mock
    private lateinit var interactor: AddContract.Interactor

    @Before
    fun setup() {

        val cicerone = Cicerone.create()
        val router = cicerone.router

        presenter = AddPresenter(view, interactor, router)
    }

    @Test
    fun addMovieWithTitle() {
        val movie = Movie(title = "Test Movie", releaseDate = "1991")
        presenter.addMovies(movie.title!!, movie.releaseDate!!)
        verify(interactor).addMovie(movie)
    }
}