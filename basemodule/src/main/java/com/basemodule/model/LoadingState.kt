package com.basemodule.model

import androidx.annotation.VisibleForTesting
import java.util.*

class LoadingState {
    @VisibleForTesting
    val loadingObjects = ArrayList<LoadingObject>()

    fun getLoadingModes(): Set<Int> {
        val loadingModes: MutableSet<Int> = HashSet()
        for (itr in loadingObjects)
            loadingModes.add(itr.loadingMode)
        return loadingModes
    }

    //return true if any change has happened
    fun addLoadingObject(loadingMode: Int, tag: Any) {
        synchronized(loadingObjects) {
            val loadingObject = loadingObjects.firstOrNull { it.tag == tag  }
            if (loadingObject != null)
                loadingObjects.remove(loadingObject)

            loadingObjects.add(LoadingObject(loadingMode, tag))
        }
    }

    fun removeLoadingObject(tag: Any?) {
        synchronized(loadingObjects) {
            val loadingObject = loadingObjects.firstOrNull { it.tag == tag  }
            if (loadingObject != null)
                loadingObjects.remove(loadingObject)
        }
    }
}

data class LoadingObject(
    val loadingMode: Int,
    val tag: Any
)