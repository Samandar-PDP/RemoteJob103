package com.sdk.remotejobs103.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.remotejobs103.database.JobDatabase
import com.sdk.remotejobs103.model.Job
import com.sdk.remotejobs103.repository.JobRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: JobRepository
) : ViewModel() {
    val userIntent = Channel<DetailIntent>()

    init {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                if (it is DetailIntent.OnSaveFavoriteJob) {
                    saveJob(it.job)
                }
            }
        }
    }

    private suspend fun saveJob(job: Job) {
        repository.saveJob(job)
    }
}