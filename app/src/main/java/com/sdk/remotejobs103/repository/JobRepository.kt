package com.sdk.remotejobs103.repository

import com.sdk.remotejobs103.network.ApiService

class JobRepository(
    private val apiService: ApiService
) {
    suspend fun getAllJobs() = apiService.getAllJobs()
    suspend fun searchJobs(query: String) = apiService.searchJob(query)
}