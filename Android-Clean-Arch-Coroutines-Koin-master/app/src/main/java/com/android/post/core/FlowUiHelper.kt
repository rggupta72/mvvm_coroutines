package com.android.post.core

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.applyUiModel() : Flow<UiModel<T>>{
    return map {
        UiModel.success(it)
    }.catch { exception ->
        emit(UiModel.error(exception))
    }.onStart{
        emit(UiModel.inProgress())
    }
}

suspend fun <T> Flow<T>.subscribe(
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit
) {
    this
        .onEach { onNext(it) }
        .onCompletion { error: Throwable? ->
            if (error == null) {
                onComplete()
            }
        }
        .catch { onError(it) }
        .collect()
}