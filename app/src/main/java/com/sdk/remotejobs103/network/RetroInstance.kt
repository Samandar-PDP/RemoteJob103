package com.sdk.remotejobs103.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://remotive.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}