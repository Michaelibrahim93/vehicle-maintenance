package com.basemodule.model

class UiError(
    var throwable: Throwable? = null,
    var errorText: String = "",
    var mustRetry: Boolean = false,
    var isServerError: Boolean = false,
    var runnable: Runnable? = null
)