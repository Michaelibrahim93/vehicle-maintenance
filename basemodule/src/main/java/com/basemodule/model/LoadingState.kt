package com.basemodule.model

import java.util.*

class LoadingState {
    private val loadingObjects =
        ArrayList<LoadingObject>()

    fun getLoadingModes(): Set<Int> {
        val loadingModes: MutableSet<Int> = HashSet()
        for (itr in loadingObjects)
            loadingModes.add(itr.loadingMode)
        return loadingModes
    }

    //return true if any change has happened
    fun addLoadingObject(loadingMode: Int, tag: Any) {
        synchronized(loadingObjects) {
            loadingObjects.add(LoadingObject(loadingMode, tag))
        }
    }

    fun removeLoadingObject(tag: Any?) {
        synchronized(loadingObjects) {
            loadingObjects.remove(loadingObjects.first { it.tag == tag  })
        }
    }

    private data class LoadingObject(val loadingMode: Int, var tag: Any)
}