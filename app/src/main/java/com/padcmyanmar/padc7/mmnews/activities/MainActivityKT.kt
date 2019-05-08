package com.padcmyanmar.padc7.mmnews.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.padcmyanmar.padc7.mmnews.R
import com.padcmyanmar.padc7.mmnews.adapters.NewsAdapter
import com.padcmyanmar.padc7.mmnews.components.SmartRecyclerView
import com.padcmyanmar.padc7.mmnews.components.SmartScrollListener
import com.padcmyanmar.padc7.mmnews.data.models.NewsModel
import com.padcmyanmar.padc7.mmnews.data.vos.NewsVO
import com.padcmyanmar.padc7.mmnews.mvp.views.NewsListView
import com.padcmyanmar.padc7.mmnews.mvp.views.presenters.NewsListPresenter
import com.padcmyanmar.padc7.mmnews.mvp.views.presenters.NewsListPresenterImpl
import com.padcmyanmar.padc7.mmnews.views.pods.EmptyViewPod
import com.padcmyanmar.padc7.mmnews.views.pods.LoginUserViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityKT : BaseActivity(), NewsListView {

    private lateinit var mNewsPresenter : NewsListPresenterImpl

    private var mBottomSheetBehavior: BottomSheetBehavior<NestedScrollView>? = null

    private var mNewsAdapter: NewsAdapter? = null

    private var mSmartScrollListener: SmartScrollListener? = null

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivityKT::class.java)
        }
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        toolbar!!.setTitle(R.string.title_latest_news)

        setSupportActionBar(toolbar)


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_lastest_news -> {
                    Toast.makeText(getApplicationContext(), getString(R.string.title_latest_news), Toast.LENGTH_SHORT).show()
                    toolbar!!.setTitle(R.string.title_latest_news)
                }
                R.id.menu_news_just_for_you -> {
                    Toast.makeText(getApplicationContext(), getString(R.string.title_news_just_for_you), Toast.LENGTH_SHORT).show()
                    toolbar!!.setTitle(R.string.title_news_just_for_you)
                }
                R.id.menu_favorite_news -> {
                    Toast.makeText(getApplicationContext(), getString(R.string.title_favorite_news), Toast.LENGTH_SHORT).show()
                    toolbar!!.setTitle(R.string.title_favorite_news)
                }
            }
            for (index in 0 until navigationView.menu.size()) {
                navigationView.menu.getItem(index).isChecked = false
            }
            menuItem.isChecked = true
            drawerLayout!!.closeDrawer(GravityCompat.START)
            false
        }

        toolbar!!.setNavigationOnClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }

        swipeRefreshLayout!!.setOnRefreshListener {
           mNewsPresenter.onUserRefresh()
        }

        mSmartScrollListener = SmartScrollListener(SmartScrollListener.OnSmartScrollListener {
            Toast.makeText(getApplicationContext(), "onListEndReach", Toast.LENGTH_LONG).show()
          mNewsPresenter.onReachEndList()
        })

        rvNews!!.addOnScrollListener(mSmartScrollListener!!)
        rvNews!!.setEmptyView(vpEmpty)
        rvNews!!.layoutManager = LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false)


        mNewsPresenter = NewsListPresenterImpl(this)

        mNewsAdapter = NewsAdapter(mNewsPresenter)
        rvNews!!.adapter = mNewsAdapter

        mNewsPresenter.onUIReady()

        var nsvBottomSheet = nsvBottomSheet
        mBottomSheetBehavior = BottomSheetBehavior.from(nsvBottomSheet)


        mBottomSheetBehavior!!.peekHeight = 0

        if (!mUserModel.isUserLogin()) {
            //User hasn't login
            navigateToLoginScreen()
            return
        }

        val vpLoginUser = navigationView.getHeaderView(0) as LoginUserViewPod
        vpLoginUser.setData(mUserModel.getLoginUser())
        vpLoginUser.setDelegate {
            mUserModel.onUserLogout()
            navigateToLoginScreen()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    /*
    fun onTapFab(view: View) {
        *//*
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
                *//*

        if (mBottomSheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            mBottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }
*/

/* delegation implement in Presenter
    override fun onTapNewsItem() {
        val intent = NewsDetailsActivity.newIntent(getApplicationContext())
        startActivity(intent)
    }*/

   /* private fun bindNews() {
        val news = mNewsModel.getNews(object : NewsModel.NewsDelegate {
            override fun onSuccess(newsList: List<NewsVO>) {
                mNewsAdapter!!.setNewData(newsList)
            }

            override fun onError(msg: String) {
                //Toast Msg.
            }
        }, true)

        if (news != null) {
            mNewsAdapter!!.setNewData(news)
        }
    }*/

    private fun navigateToLoginScreen() {
        val intent = LoginActivity.newIntent(getApplicationContext())
        startActivity(intent)
    }


    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext,"Error Loading News", Toast.LENGTH_SHORT).show()
    }

    override fun displayNewsList(newsList: MutableList<NewsVO>) {
        mNewsAdapter?.setNewData(newsList)
    }

    override fun refreshNews(newsList: MutableList<NewsVO>) {
        mNewsAdapter?.setNewData(newsList)
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun loadMoreNews(newsList: MutableList<NewsVO>) {
        mNewsAdapter!!.appendNewData(newsList)
    }


    override fun goDetailsNews() {
        val intent = Intent(applicationContext, NewsDetailsActivityKT::class.java)
        startActivity(intent)
    }


}
