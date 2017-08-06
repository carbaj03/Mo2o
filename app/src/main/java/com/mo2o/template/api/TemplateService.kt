package com.mo2o.template.api


import com.mo2o.template.api.model.Contributor
import com.mo2o.template.api.model.Repo
import com.mo2o.template.api.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TemplateService {
    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Call<User>

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): Call<List<Repo>>

    @GET("repos/{owner}/{name}")
    fun getRepo(@Path("owner") owner: String, @Path("name") name: String): Call<Repo>

    @GET("repos/{owner}/{name}/contributors")
    fun getContributors(@Path("owner") owner: String, @Path("name") name: String): Call<List<Contributor>>
}
