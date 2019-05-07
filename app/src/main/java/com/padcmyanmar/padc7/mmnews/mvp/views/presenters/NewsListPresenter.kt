package com.padcmyanmar.padc7.mmnews.mvp.views.presenters

import com.padcmyanmar.padc7.mmnews.delegates.NewsItemDelegate

interface NewsListPresenter: NewsItemDelegate {

    fun onUIReady()
    fun onReachEndList()
    fun onUserRefresh()



}