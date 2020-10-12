package com.oleg.androidviper.presenter

import com.oleg.androidviper.MainContract
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.view.activities.AddMovieActivity
import com.oleg.androidviper.view.activities.MainActivity
import ru.terrakok.cicerone.Router

class MainPresenter(
    private var view: MainContract.View?,
    private var interactor: MainContract.Interactor?,
    private val router: Router?
) : MainContract.Presenter, MainContract.InteractorOutput {

    override fun addMovie() {
        router?.navigateTo(AddMovieActivity::class.simpleName)
    }

    override fun deleteMovies(selectedMovies: HashSet<Movie>) {
        for (movie in selectedMovies) {
            interactor?.deleteMovie(movie)
        }
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun onViewCreated() {
        view?.showLoading()
        interactor?.loadMovieList()?.observe((view as MainActivity), { movieList ->
            if (movieList == null) {
                onQueryError()
            } else {
                onQuerySuccess(movieList)
            }
        })
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showMessage("Error loading data")
    }

    override fun onQuerySuccess(data: List<Movie>) {
        view?.hideLoading()
        view?.displayMovieList(data)
    }
}