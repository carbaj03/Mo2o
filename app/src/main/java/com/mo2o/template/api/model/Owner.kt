package com.mo2o.template.api.model

import com.google.gson.annotations.SerializedName

data class Owner(
        @SerializedName("login") val login: String,
        @SerializedName("url") val url: String
)