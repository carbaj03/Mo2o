package com.mo2o.template.api.model

import com.google.gson.annotations.SerializedName

data class Follow(
        @SerializedName("login") val login: String,
        @SerializedName("avatar_url") val avatarUrl: String,
        @SerializedName("id") val name: Int
)
