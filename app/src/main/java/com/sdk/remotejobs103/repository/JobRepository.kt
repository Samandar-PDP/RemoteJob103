package com.sdk.remotejobs103.repository

import com.sdk.remotejobs103.database.JobDao
import com.sdk.remotejobs103.model.Job
import com.sdk.remotejobs103.network.ApiService

class JobRepository(
    private val apiService: ApiService?,
    private val dao: JobDao?
) {
    suspend fun getAllJobs() = apiService?.getAllJobs()
    suspend fun searchJobs(query: String) = apiService?.searchJob(query)

    suspend fun saveJob(job: Job) = dao?.saveJob(job)
    fun getFavoriteJobs() = dao?.getFavoritesJobs()
    suspend fun deleteJob(job: Job) = dao?.deleteJob(job)
}