package com.oleg.androidviper.presenter

import com.oleg.androidviper.contracts.AddContract
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.view.activities.MainActivity
import com.oleg.androidviper.view.activities.SearchMovieActivity
import ru.terrakok.cicerone.Router

class AddPresenter(
    private var view: AddContract.View?,
    private var interactor: AddContract.Interactor?,
    private val router: Router?
) : AddContract.Presenter {

    override fun addMovies(title: String, year: String) {
        if (title.isNotBlank()) {
            router?.navigateTo(MainActivity::class.simpleName)
            interactor?.addMovie(Movie(title = title, releaseDate = year))
        } else {
            view?.showMessage("Enter movie title")
        }
    }

    override fun searchMovies(title: String) {
        if (title.isNotBlank()) {
            router?.navigateTo(SearchMovieActivity::class.simpleName)
        } else {
            view?.showMessage("Enter movie title")
        }
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }

}
