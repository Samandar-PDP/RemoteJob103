package com.sdk.remotejobs103.network

import com.sdk.remotejobs103.model.JobsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("remote-jobs")
    suspend fun getAllJobs(): Response<JobsResponse>

    @GET("remote-jobs")
    suspend fun searchJob(
        @Query("search") query: String
    ): Response<JobsResponse>
}