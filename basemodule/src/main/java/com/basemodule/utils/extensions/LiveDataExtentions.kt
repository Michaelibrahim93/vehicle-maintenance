package com.basemodule.utils.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<*>.setValue() {
    value = value
}

fun<T> MutableLiveData<T>.postValue() {
    postValue(this.value)
}