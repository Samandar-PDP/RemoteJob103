package com.sdk.remotejobs103.search

import com.sdk.remotejobs103.model.Job

sealed class SearchState {
    object Loading: SearchState()
    data class Error(val msg: String): SearchState()
    data class Success(val list: List<Job>): SearchState()
}