package com.sdk.remotejobs103.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.remotejobs103.repository.JobRepository

class DetailViewModelFactory(
    private val repository: JobRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DetailViewModel(repository) as T
    }
}