package com.android.post.di

import com.android.post.BuildConfig
import com.android.post.data.repository.PostsRepositoryImp
import com.android.post.data.source.remote.ApiService
import com.android.post.domain.repository.PostsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {

    single { createService(get()) }
    single { provideGson() }
    single { ResponseWrapper() }
    single { provideGsonConverterFactory(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL, get(),get()) }

    single { createOkHttpClient() }


}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(
    okHttpClient: OkHttpClient,
    url: String,
    responseWrapper: ResponseWrapper,
     gsonConverterFactory:GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(responseWrapper)
        .addConverterFactory(gsonConverterFactory).build()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createPostRepository(apiService: ApiService): PostsRepository {
    return PostsRepositoryImp(apiService)
}

fun provideGsonConverterFactory(gson:Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}
fun provideGson(): Gson {
    return GsonBuilder().create()
}
