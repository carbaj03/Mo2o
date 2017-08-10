package com.mo2o.template.infrastructure.api.model

import com.google.gson.annotations.SerializedName

data class Directory(
        @SerializedName("type") val type: String,
        @SerializedName("size") val size: Int,
        @SerializedName("name") val name: String,
        @SerializedName("path") val path: String
)
