package com.sdk.remotejobs103.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.remotejobs103.repository.JobRepository

class FavoriteViewModelFactory(
    private val repository: JobRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FavoriteViewModel(repository) as T
    }
}