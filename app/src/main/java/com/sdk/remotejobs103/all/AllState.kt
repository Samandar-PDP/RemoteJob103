package com.sdk.remotejobs103.all

import com.sdk.remotejobs103.model.Job

sealed class AllState {
    object Loading: AllState()
    data class Error(val message: String): AllState()
    data class Success(val jobs: List<Job>): AllState()
}