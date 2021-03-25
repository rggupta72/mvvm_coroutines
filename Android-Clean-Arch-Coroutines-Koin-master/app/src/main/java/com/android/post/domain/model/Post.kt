package com.android.post.domain.model

import com.google.gson.annotations.SerializedName


data class Post(
    @JvmField @SerializedName("userId") val userId: Int,
    @JvmField @SerializedName("id") val id: Int,
    @JvmField @SerializedName("title") val title: String,
    @JvmField @SerializedName("body") val body: String
)