package com.android.post.presentation.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.post.core.UiModel
import com.android.post.core.applyUiModel
import com.android.post.domain.model.Post
import com.android.post.domain.repository.PostsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PostsViewModel constructor(
    val repository: PostsRepository
) : ViewModel() {

    val postsData = MutableLiveData<UiModel<List<Post>>>()

    fun getPosts() {
        viewModelScope.launch {
            repository.getPosts()
                .applyUiModel()
                .collect { postsData.value = it }
        }


    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    companion object {
        private val TAG = PostsViewModel::class.java.name
    }

}