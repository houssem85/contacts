package com.streamwide.contacts.utils

data class Resource<out T> constructor(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
        fun <T> empty(): Resource<T> {
            return Resource(Status.EMPTY, null, null)
        }
    }
}
