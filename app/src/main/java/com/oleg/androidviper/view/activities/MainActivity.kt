package com.oleg.androidviper.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import com.oleg.androidviper.App
import com.oleg.androidviper.MainContract
import com.oleg.androidviper.R
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.interactor.MainInteractor
import com.oleg.androidviper.presenter.MainPresenter
import com.oleg.androidviper.view.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import timber.log.Timber

class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapter: MovieListAdapter
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private val router: Router? by lazy { App.INSTANCE.cicerone.router }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command?) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
                    AddMovieActivity::class.simpleName -> startActivity(
                        Intent(
                            this@MainActivity,
                            AddMovieActivity::class.java
                        )
                    )
                    else -> Timber.d("Unknown screen: %s", command.screenKey)
                }
            }
        }
    }

    private fun goToSearchActivity() = presenter.addMovie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, MainInteractor(), router)
        fab.setOnClickListener { goToSearchActivity() }
    }

    override fun deleteMoviesClicked() {
        presenter.deleteMovies(adapter.selectedMovies)
    }

    override fun hideLoading() {
        moviesRecyclerView.isEnabled = true
        progressBar.visibility = GONE
    }

    override fun showLoading() {
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = VISIBLE
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun displayMovieList(movieList: List<Movie>) {
        adapter = MovieListAdapter(movieList)
        moviesRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewCreated()
        App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> this.deleteMoviesClicked()
            else -> toast(getString(R.string.error))
        }
        return super.onOptionsItemSelected(item)
    }

}