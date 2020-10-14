package com.oleg.androidviper.presenter

import com.oleg.androidviper.contracts.MainContract
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.view.activities.AddMovieActivity
import com.oleg.androidviper.view.activities.MainActivity
import ru.terrakok.cicerone.Router
import timber.log.Timber

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
            Timber.d("Movies: %s", movieList)
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

    override fun onQueryEmpty() {
        view?.hideLoading()
        view?.showMessage("No movies were added")
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