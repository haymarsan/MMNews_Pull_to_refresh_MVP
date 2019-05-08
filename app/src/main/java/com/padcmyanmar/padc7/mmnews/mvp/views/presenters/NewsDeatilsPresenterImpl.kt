package com.padcmyanmar.padc7.mmnews.mvp.views.presenters

import com.padcmyanmar.padc7.mmnews.mvp.views.NewsDetailsView

class NewsDeatilsPresenterImpl(newsDetailsView: NewsDetailsView) : BasePresenter<NewsDetailsView> (newsDetailsView), NewsDetailsPresenter {

    override fun goToDetailsNews() {
        mView.showNewsDetails()
    }

    override fun onStart() {

    }

    override fun onStop() {

     }



}