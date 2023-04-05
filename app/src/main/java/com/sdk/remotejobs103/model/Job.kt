package com.sdk.remotejobs103.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Job(
    val candidate_required_location: String?,
    val category: String?,
    val company_logo: String?,
    val company_logo_url: String?,
    val company_name: String?,
    val description: String?,
    val id: Int?,
    val job_type: String?,
    val publication_date: String?,
    val salary: String?,
    val title: String?,
    val url: String?,
): Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0
}