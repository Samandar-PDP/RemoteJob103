package com.sdk.remotejobs103.model

data class Job(
    val candidate_required_location: String,
    val category: String,
    val company_logo: String,
    val company_logo_url: String,
    val company_name: String,
    val description: String,
    val id: Int,
    val job_type: String,
    val publication_date: String,
    val salary: String,
    val tags: List<String>,
    val title: String,
    val url: String
)