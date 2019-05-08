package com.padcmyanmar.padc7.mmnews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.padcmyanmar.padc7.mmnews.R
import com.padcmyanmar.padc7.mmnews.adapters.NewsDetailsImagesAdapter
import com.padcmyanmar.padc7.mmnews.mvp.views.NewsDetailsView
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivityKT : BaseActivity(), NewsDetailsView {

    fun newIntent(context: Context): Intent {
        return Intent(context, NewsDetailsActivityKT::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        setSupportActionBar(toolbar)

      //  val vpNewsDetailsImages = vpNewsDetailsImages

    }

    override fun showNewsDetails() {
        val newsDetailsImagesAdapter = NewsDetailsImagesAdapter()
        vpNewsDetailsImages.adapter = newsDetailsImagesAdapter
    }


}