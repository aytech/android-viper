package com.oleg.androidviper.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.oleg.androidviper.App
import com.oleg.androidviper.R
import com.oleg.androidviper.action
import com.oleg.androidviper.contracts.SearchContract
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.interactor.SearchInteractor
import com.oleg.androidviper.presenter.SearchPresenter
import com.oleg.androidviper.snack
import com.oleg.androidviper.view.adapters.SearchListAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import timber.log.Timber

class SearchMovieActivity : BaseActivity(), SearchContract.View {

    private var presenter: SearchContract.Presenter? = null
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private val router: Router? by lazy { App.INSTANCE.cicerone.router }
    private lateinit var adapter: SearchListAdapter

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Back) {
                    back()
                }
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
                    MainActivity::class.simpleName -> startActivity(
                        Intent(this@SearchMovieActivity, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                    else -> Timber.d("Unknown screen: %s", command.screenKey)
                }
            }

            private fun back() {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        presenter = SearchPresenter(this, SearchInteractor(), router)
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun showLoading() {
        searchProgressBar.visibility = VISIBLE
        searchRecyclerView.isEnabled = false
    }

    override fun hideLoading() {
        searchProgressBar.visibility = GONE
        searchRecyclerView.isEnabled = true
    }

    override fun showMessage(message: String) {
        searchLayout.snack(message, LENGTH_LONG) {
            action(getString(R.string.ok)) {
                router?.navigateTo(AddMovieActivity::class.simpleName)
            }
        }
    }

    override fun showRetryMessage() {
        searchLayout.snack(getString(R.string.network_error), LENGTH_INDEFINITE) {
            action(getString(R.string.yes)) {
                intent.extras?.getString("title")?.let { title -> presenter?.searchMovies(title) }
            }
        }
    }

    override fun displayMovieList(movies: List<Movie>) {
        adapter = SearchListAdapter(movies) { movie -> presenter?.movieClicked(movie) }
        searchRecyclerView.adapter = adapter
    }

    override fun displayConfirmation(movie: Movie?) {
        searchLayout.snack("Add ${movie?.title} to your list?", LENGTH_LONG) {
            action(getString(R.string.yes)) {
                presenter?.addMovieClicked(movie)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        intent.extras?.getString("title")?.let { title -> presenter?.searchMovies(title) }
        App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

}
