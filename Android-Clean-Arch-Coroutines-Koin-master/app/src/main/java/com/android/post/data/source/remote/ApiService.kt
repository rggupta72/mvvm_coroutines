package com.android.post.data.source.remote

import com.android.post.di.ExcludeWrapper
import com.android.post.domain.model.Post
import retrofit2.http.GET

interface ApiService {

    @ExcludeWrapper
    @GET("/posts")
    suspend fun getPosts(): List<Post>
}