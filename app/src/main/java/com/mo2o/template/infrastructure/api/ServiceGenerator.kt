package com.mo2o.template.infrastructure.api

import android.text.TextUtils
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGenerator {

    val API_BASE_URL = "https://api.github.com/"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private var retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S = createService(serviceClass, null, null)

    fun <S> createService(serviceClass: Class<S>, username: String?, password: String?): S {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            val authToken = Credentials.basic(username!!, password!!)
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
            return createService(serviceClass, authToken)
        }

        return createService(serviceClass, null, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: String): S {
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken)

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)

                builder.client(httpClient.build())

                retrofit = builder.build()
            }
        }
        return retrofit.create(serviceClass)
    }
}