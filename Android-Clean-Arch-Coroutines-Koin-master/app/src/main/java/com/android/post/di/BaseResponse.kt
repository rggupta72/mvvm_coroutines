package com.android.post.di

import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(
  @JvmField @SerializedName("result") val result: T?,
) {
  val isSuccessful: Boolean
    get() =  result != null

  fun getMessage(): String {
    return  if(result == null) "An Error Occurred"
               else ""
  }
}




