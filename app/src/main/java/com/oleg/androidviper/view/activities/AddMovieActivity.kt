package com.oleg.androidviper.view.activities

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.oleg.androidviper.AddContract
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*

class AddMovieActivity : BaseActivity(), AddContract.View {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    override fun showMessage(msg: String) {
        // TODO("Not yet implemented")
    }

    override fun searchMovieClicked(view: View) {
        // TODO("Not yet implemented")
    }

    override fun addMovieClicked(view: View) {
        // TODO("Not yet implemented")
    }

    override fun getToolbarInstance(): Toolbar? = toolbar
}