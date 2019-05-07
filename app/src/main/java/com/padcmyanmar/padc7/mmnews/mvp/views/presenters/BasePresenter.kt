package com.padcmyanmar.padc7.mmnews.mvp.views.presenters

abstract class BasePresenter <T> (val mView : T){

    open fun onCreate(){}
    abstract fun onStart()
    abstract fun onStop()
    open fun onDistory(){}


}