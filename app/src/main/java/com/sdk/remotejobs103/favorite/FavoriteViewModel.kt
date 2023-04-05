package com.sdk.remotejobs103.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.remotejobs103.model.Job
import com.sdk.remotejobs103.repository.JobRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: JobRepository
) : ViewModel() {
    private val _jobList: MutableLiveData<List<Job>> = MutableLiveData(emptyList())
    val jobList: LiveData<List<Job>> get() = _jobList

    init {
        viewModelScope.launch {
            repository.getFavoriteJobs()?.collectLatest {
                _jobList.postValue(it)
            }
        }
    }
    fun deleteJob(job: Job) {
        viewModelScope.launch {
            repository.deleteJob(job)
        }
    }
}