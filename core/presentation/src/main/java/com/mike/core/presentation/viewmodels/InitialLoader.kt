package com.mike.core.presentation.viewmodels

interface InitialLoader {
    fun loadInitValues(block: () -> Unit)
}

class InitLoaderImpl : InitialLoader {
    var hasLoaded = false

    override fun loadInitValues(block: () -> Unit) {
        if (!hasLoaded) {
            block()
            hasLoaded = true
        }
    }
}