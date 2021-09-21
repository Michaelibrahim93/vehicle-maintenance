package com.basemodule.ui

interface LoadingViewOwner {
    fun showLoading(loadingMode: Int)
    fun hideLoading(loadingMode: Int)
}