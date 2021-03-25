package com.android.post.core

inline fun <T> UiModel<T>.onSuccess(action: (data: T) -> Unit) {
    if (this is UiModel.Success) {
        action(this.value)
    }
}

inline fun UiModel<*>.onFailure(action: (error: Throwable) -> Unit) {
    if (this is UiModel.Fail) {
        action(this.throwable)
    }
}