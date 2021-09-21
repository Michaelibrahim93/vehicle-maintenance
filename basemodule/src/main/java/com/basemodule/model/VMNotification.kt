package com.basemodule.model

class VMNotification(
    val action: String,
    private val tag: Any? = null
) {
    @Suppress("UNCHECKED_CAST")
    fun<T> getTag(tClass: Class<T>): T? {
        return if (tag != null && (tag.javaClass == tClass || tag.javaClass.isAssignableFrom(
                tClass
            ))
        ) tag as T else null
    }
}