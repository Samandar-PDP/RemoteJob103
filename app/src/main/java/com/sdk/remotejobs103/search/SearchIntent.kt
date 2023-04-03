package com.sdk.remotejobs103.search

sealed class SearchIntent {
    data class OnSearch(val query: String): SearchIntent()
}