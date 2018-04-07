package com.victor.test.sport.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.victor.test.sport.R
import com.victor.test.sport.data.TypeDto
import com.victor.test.sport.presenters.reviews.SportMenPresenter
import com.victor.test.sport.ui.SpaceDecorator
import com.victor.test.sport.ui.SportMenAdapter
import com.victor.test.sport.utils.app
import com.victor.test.sport.utils.getDpFromValue
import com.victor.test.sport.utils.trace
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SportMenPresenter.SportMenView {

    private val component by lazy { app.component.plus(MainActivityModule(this)) }

    @Inject lateinit var sportMenPresenter: SportMenPresenter
    private val sportMenList = ArrayList<TypeDto>()
    private lateinit var sportMenAdapter: SportMenAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)


        setSupportActionBar(toolBar)
        toolBar.inflateMenu(R.menu.main_menu)

        val linearLayoutManager = LinearLayoutManager(this)
        lstReviews.layoutManager = linearLayoutManager
        lstReviews.addItemDecoration(SpaceDecorator(getDpFromValue(this, 10)))
        sportMenAdapter = SportMenAdapter(sportMenList)
        lstReviews.adapter = sportMenAdapter


        edtFilter.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sportMenAdapter.filter.filter(s.toString())
            }
        })


        sportMenPresenter.view = this
        sportMenPresenter.getSportMenList()
    }

    override fun onDestroy() {
        super.onDestroy()
        sportMenPresenter.destroy()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                if (edtFilter.visibility == View.GONE) {
                    edtFilter.visibility = View.VISIBLE
                } else {
                    edtFilter.setText("")
                    edtFilter.visibility = View.GONE
                }

            }
            else -> {}
        }

        return true
    }



    // ------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- REVIEWS VIEW INTERFACE ---------------------------------------------
    override fun onSportMenListReceived(sportMenResponse: ArrayList<TypeDto>) {
        trace("MainActivity - sportMenResponse :: ${sportMenResponse.size}")
        sportMenList.clear()
        sportMenList.addAll(sportMenResponse)
        sportMenAdapter.updateList(sportMenList)
    }

    override fun onReviewsListError() {
        Snackbar.make(mainLayout, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show()
    }




    // -----------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- METHODS AND RUNNABLES ---------------------------------------------

}
