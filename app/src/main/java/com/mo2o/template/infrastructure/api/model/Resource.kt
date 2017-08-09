package com.mo2o.template.infrastructure.api.model

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    override fun toString() =
            """Resource{ status=$status , message='$message\', data=$data }"""

    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(SUCCESS, data, null)
        fun <T> error(msg: String, data: T?): Resource<T> = Resource(ERROR, data, msg)
        fun <T> loading(data: T?): Resource<T> = Resource(LOADING, data, null)
    }
}
