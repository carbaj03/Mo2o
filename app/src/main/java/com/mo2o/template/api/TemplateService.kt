package com.mo2o.template.api


import com.mo2o.template.api.model.Contributor
import com.mo2o.template.api.model.Follow
import com.mo2o.template.api.model.Repo
import com.mo2o.template.api.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TemplateService {

    @GET("user")
    fun getUser() : Call<User>

    @GET("user/repos")
    fun getRepos(): Call<List<Repo>>

    @GET("user/starred")
    fun getStarred(): Call<List<Repo>>

    @GET("user/following")
    fun getFollowing(): Call<List<Follow>>


    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Call<User>

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): Call<List<Repo>>

    @GET("repos/{owner}/{login}")
    fun getRepo(@Path("owner") owner: String, @Path("login") name: String): Call<Repo>

    @GET("repos/{owner}/{login}/contributors")
    fun getContributors(@Path("owner") owner: String, @Path("login") name: String): Call<List<Contributor>>
}
