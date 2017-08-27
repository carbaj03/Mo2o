package com.mo2o.template.infrastructure.api

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

const val auth : String = "Authorization"


class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(getBulder(chain).build())
    }

    fun getBulder(chain: Interceptor.Chain) =
            chain.request().newBuilder().header(auth, authToken)

}