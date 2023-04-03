package com.sdk.remotejobs103.model

data class JobsResponse(
    val `0-legal-notice`: String,
    val `00-warning`: String,
    val jobs: List<Job>
)