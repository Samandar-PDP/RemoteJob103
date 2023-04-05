package com.sdk.remotejobs103.detail

import com.sdk.remotejobs103.model.Job

sealed class DetailIntent {
    data class OnSaveFavoriteJob(val job: Job): DetailIntent()
}