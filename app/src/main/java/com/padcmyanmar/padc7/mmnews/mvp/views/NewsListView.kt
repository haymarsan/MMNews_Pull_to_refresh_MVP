package com.padcmyanmar.padc7.mmnews.mvp.views

import com.padcmyanmar.padc7.mmnews.data.vos.NewsVO

interface NewsListView {

    fun showPrompt(message : String)
    fun displayNewsList(newsList: MutableList<NewsVO>)
    fun refreshNews (newsList: MutableList<NewsVO>)
    fun loadMoreNews (newsList: MutableList<NewsVO>)

}