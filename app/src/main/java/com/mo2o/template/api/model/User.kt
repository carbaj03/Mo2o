package com.mo2o.template.api.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("login") val login: String,
        @SerializedName("avatar_url") val avatarUrl: String,
        @SerializedName("name") val name: String,
        @SerializedName("email") val email: String,
        @SerializedName("company") val company: String,
        @SerializedName("repos_url") val reposUrl: String,
        @SerializedName("blog") val blog: String
)
