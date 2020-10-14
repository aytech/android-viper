package com.oleg.androidviper.presenter

import com.oleg.androidviper.contracts.SearchContract
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.view.activities.MainActivity
import com.oleg.androidviper.view.activities.SearchMovieActivity
import ru.terrakok.cicerone.Router
import timber.log.Timber

class SearchPresenter(
    private var view: SearchContract.View?,
    private var interactor: SearchContract.Interactor?,
    private val router: Router?
) : SearchContract.Presenter, SearchContract.InteractorOutput {

    override fun searchMovies(title: String) {
        view?.showLoading()
        interactor?.searchMovies(title)?.observe(view as SearchMovieActivity, { movieList ->
            Timber.d("Got response: %s", movieList)
            when {
                movieList == null -> {
                    onQueryError()
                }
                movieList.isEmpty() -> {
                    onQueryEmpty()
                }
                else -> {
                    onQuerySuccess(movieList)
                }
            }
        })
    }

    override fun addMovieClicked(movie: Movie?) {
        interactor?.addMovie(movie)
        router?.navigateTo(MainActivity::class.simpleName)
    }

    override fun movieClicked(movie: Movie?) {
        view?.displayConfirmation(movie)
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

    override fun onQuerySuccess(movies: List<Movie>) {
        view?.hideLoading()
        view?.displayMovieList(movies)
    }

    override fun onQueryEmpty() {
        view?.hideLoading()
        view?.showMessage("Movies not found")
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showRetryMessage()
    }

}
