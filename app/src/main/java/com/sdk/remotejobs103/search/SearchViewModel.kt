package com.sdk.remotejobs103.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.remotejobs103.network.RetroInstance
import com.sdk.remotejobs103.repository.JobRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = JobRepository(RetroInstance.provideApiService(),null)
    private val _state: MutableLiveData<SearchState> = MutableLiveData()
    val state: LiveData<SearchState> get() = _state

    val intent = Channel<SearchIntent>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is SearchIntent.OnSearch -> searchJobs(it.query)
                }
            }
        }
    }

    private fun searchJobs(query: String) {
        viewModelScope.launch {
            _state.postValue(SearchState.Loading)
            try {
                val response = repository.searchJobs(query)
                if (response?.isSuccessful == true) {
                    _state.postValue(SearchState.Success(response.body()?.jobs ?: emptyList()))
                }
            } catch (e: Exception) {
                _state.postValue(SearchState.Error(e.message.toString()))
            }
        }
    }
}