package com.android.post.di.module

import com.android.post.di.createPostRepository
import com.android.post.presentation.posts.PostsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { PostsViewModel(get()) }


    single { createPostRepository(get()) }
}