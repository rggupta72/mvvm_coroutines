package com.android.post.data.repository

import com.android.post.data.source.remote.ApiService
import com.android.post.domain.model.Post
import com.android.post.domain.repository.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PostsRepositoryImp(private val apiService: ApiService) : PostsRepository {

    override fun getPosts(): Flow<List<Post>> {
        return flow {
            val result = apiService.getPosts()
            emit(result)
        }.map { it }.flowOn(Dispatchers.IO)
    }
}