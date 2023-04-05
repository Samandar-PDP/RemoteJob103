package com.sdk.remotejobs103.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.remotejobs103.model.Job
import com.sdk.remotejobs103.network.RetroInstance
import com.sdk.remotejobs103.repository.JobRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AllViewModel : ViewModel() {
    private val repository = JobRepository(RetroInstance.provideApiService(),null)

    private val _allState: MutableLiveData<AllState> = MutableLiveData()
    val allState: LiveData<AllState> get() = _allState

    val userIntent = Channel<AllIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is AllIntent.OnGetAllJobs -> getAllJobs()
                }
            }
        }
    }

    private fun getAllJobs() {
        viewModelScope.launch {
            _allState.postValue(AllState.Loading)
            try {
                val response = repository.getAllJobs()
                if (response?.isSuccessful == true) {
                    _allState.postValue(AllState.Success(response?.body()?.jobs ?: emptyList()))
                }
            } catch (e: Exception) {
                _allState.postValue(AllState.Error(e.message.toString()))
            }
        }
    }
}