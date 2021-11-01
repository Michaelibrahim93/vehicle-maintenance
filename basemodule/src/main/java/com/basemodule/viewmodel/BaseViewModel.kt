package com.basemodule.viewmodel

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.basemodule.utils.extensions.postValue
import com.basemodule.utils.extensions.setValue
import com.basemodule.model.LoadingState
import com.basemodule.model.UiError
import com.basemodule.model.VMNotification
import timber.log.Timber
import java.util.HashSet

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val ldLoadingState: MutableLiveData<LoadingState> = MutableLiveData()
    val ldActions: MutableLiveData<MutableSet<VMNotification>> = MutableLiveData()
    val ldUiError: MutableLiveData<UiError> = MutableLiveData<UiError>()

    val context: Context
        get() = getApplication()

    init {
        ldActions.value = HashSet()
        ldLoadingState.value = LoadingState()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun addLoadingObject(
        loadingMode: Int,
        tag: Any,
        threadSafe: Boolean = false
    ) {
        ldLoadingState.value!!.addLoadingObject(loadingMode, tag)
        if (threadSafe) ldLoadingState.postValue() else ldLoadingState.setValue()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun removeLoadingObject(tag: Any?, threadSafe: Boolean = false) {
        ldLoadingState.value!!.removeLoadingObject(tag)
        if (threadSafe)
            ldLoadingState.postValue()
        else
            ldLoadingState.setValue()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun addAction(action: String?, tag: Any? = null, threadSafe: Boolean = false) {
        ldActions.value!!.add(VMNotification(action!!, tag))

        if (threadSafe) ldActions.postValue()
        else ldActions.setValue()
    }

    fun clearActions() {
        ldActions.value!!.clear()
    }

    fun handleNetworkError(
        throwable: Throwable,
        mustRetry: Boolean,
        runnable: Runnable?
    ) {
        Timber.w(throwable)
        ldUiError.postValue(createUiErrorModel(throwable, mustRetry, runnable))
    }

    open fun createUiErrorModel(throwable: Throwable, mustRetry: Boolean,runnable: Runnable?): UiError {
        return UiError(
            throwable, throwable.toString(), mustRetry
            , false
            , runnable
        )
    }

    fun handleNetworkError(throwable: Throwable) {
        handleNetworkError(throwable, false, null)
    }

    fun clearUiError() {
        ldUiError.postValue(null)
    }
}