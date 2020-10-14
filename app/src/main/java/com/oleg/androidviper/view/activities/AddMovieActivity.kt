package com.oleg.androidviper.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.oleg.androidviper.App
import com.oleg.androidviper.R
import com.oleg.androidviper.action
import com.oleg.androidviper.contracts.AddContract
import com.oleg.androidviper.interactor.AddInteractor
import com.oleg.androidviper.presenter.AddPresenter
import com.oleg.androidviper.snack
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import timber.log.Timber

class AddMovieActivity : BaseActivity(), AddContract.View {

    private var presenter: AddContract.Presenter? = null
    private val router: Router? by lazy { App.INSTANCE.cicerone.router }
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

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
                    SearchMovieActivity::class.simpleName -> startActivity(
                        Intent(this@AddMovieActivity, SearchMovieActivity::class.java)
                            .putExtra("title", titleEditText.text.toString())
                    )
                    MainActivity::class.simpleName -> startActivity(
                        Intent(this@AddMovieActivity, MainActivity::class.java)
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
        setContentView(R.layout.activity_add)
        presenter = AddPresenter(this, AddInteractor(), router)
    }

    override fun showMessage(message: String) {
        addLayout.snack(message, LENGTH_LONG) {
            action(getString(R.string.ok)) {}
        }
    }

    override fun onResume() {
        super.onResume()
        App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun searchMovieClicked(view: View) {
        presenter?.searchMovies(titleEditText.text.toString())
    }

    override fun addMovieClicked(view: View) {
        presenter?.addMovies(titleEditText.text.toString(), yearEditText.text.toString())
    }

    override fun getToolbarInstance(): Toolbar? = toolbar
}