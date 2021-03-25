package com.android.post.domain.repository

import com.android.post.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getPosts(): Flow<List<Post>>
}