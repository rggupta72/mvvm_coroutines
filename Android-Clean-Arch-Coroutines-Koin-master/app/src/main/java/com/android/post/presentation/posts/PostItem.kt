package com.android.post.presentation.posts

import com.android.post.R
import com.android.post.databinding.HolderPostBinding
import com.android.post.domain.model.Post
import com.xwray.groupie.databinding.BindableItem

class PostItem(val post: Post) : BindableItem<HolderPostBinding>() {

    override fun getLayout(): Int {
        return R.layout.holder_post
    }


    override fun bind(viewBinding: HolderPostBinding, position: Int) {
        with(viewBinding) {
            postTitleTextView.text = post.title
            postBodyTextView.text = post.body
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostItem

        if (post != other.post) return false

        return true
    }

    override fun hashCode(): Int {
        return post.hashCode()
    }
}