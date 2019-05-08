package com.padcmyanmar.padc7.mmnews.mvp.views.presenters

import android.content.Intent
import android.widget.Toast
import com.padcmyanmar.padc7.mmnews.data.models.NewsModel
import com.padcmyanmar.padc7.mmnews.data.models.NewsModelImpl
import com.padcmyanmar.padc7.mmnews.data.vos.NewsVO
import com.padcmyanmar.padc7.mmnews.mvp.views.NewsListView
import kotlinx.android.synthetic.main.activity_main.*

class NewsListPresenterImpl(newsListView: NewsListView) : BasePresenter<NewsListView>(newsListView), NewsListPresenter {


    private val mNewsModel : NewsModel = NewsModelImpl.getInstance()

    override fun onUIReady() {

              val news = mNewsModel.getNews(object : NewsModel.NewsDelegate {
            override fun onSuccess(newsList: List<NewsVO>) {
                //mNewsAdapter!!.setNewData(newsList)  // adapter job will do in MainActivity so that need to create method in Views
                mView.displayNewsList(newsList.toMutableList())
            }

            override fun onError(msg: String) {
                //Toast Msg.
                mView.showPrompt(msg)
            }
        }, true)

        if (news != null) {
            //mNewsAdapter!!.setNewData(news)
            mView.displayNewsList(news)
        }

    }

    override fun onReachEndList() {
        mNewsModel.loadMoreNews(object : NewsModel.NewsDelegate {
            override fun onSuccess(newsList: List<NewsVO>) {
                //mNewsAdapter.appendNewData(newsList);
                //mNewsAdapter!!.setNewData(newsList)
                mView.loadMoreNews(newsList.toMutableList())

            }

            override fun onError(msg: String) {
               // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show()
                mView.showPrompt(msg)
            }
        })
    }

    override fun onUserRefresh() {
        mNewsModel.getNews(object : NewsModel.NewsDelegate {
            override fun onSuccess(newsList: List<NewsVO>) {
//                mNewsAdapter!!.setNewData(newsList)
//                swipeRefreshLayout!!.isRefreshing = false
                mView.refreshNews(newsList.toMutableList())
            }

            override fun onError(msg: String) {
                mView.showPrompt(msg)
            }
        }, true)

    }

    override fun onTapNewsItem() {

        mView.goDetailsNews()


    }

    override fun onStart() {

    }

    override fun onStop() {

    }


}