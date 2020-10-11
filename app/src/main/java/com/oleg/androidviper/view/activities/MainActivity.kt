package com.oleg.androidviper.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.oleg.androidviper.MainContract
import com.oleg.androidviper.R
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.view.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete -> this.deleteMoviesClicked()
            else -> toast(getString(R.string.error))
        }
        return super.onOptionsItemSelected(item)
    }

    fun goToSearchActivity(view: View) = presenter.addMovie()
}