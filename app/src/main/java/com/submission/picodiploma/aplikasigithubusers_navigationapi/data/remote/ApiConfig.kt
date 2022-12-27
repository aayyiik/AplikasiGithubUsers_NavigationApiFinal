package com.submission.picodiploma.aplikasigithubusers_navigationapi.data.remote


import com.submission.picodiploma.aplikasigithubusers_navigationapi.BuildConfig.BASE_URL
import com.submission.picodiploma.aplikasigithubusers_navigationapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiConfig {

            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

    object AuthorizationInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val requestWithHeader = chain.request()
                .newBuilder()
                .header(
                    "Authorization", BuildConfig.TOKEN
                ).build()
            return chain.proceed(requestWithHeader)
        }
    }

            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(AuthorizationInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val githubService = retrofit.create<GithubService>()

}