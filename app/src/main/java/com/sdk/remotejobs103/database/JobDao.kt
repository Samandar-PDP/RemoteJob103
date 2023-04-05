package com.sdk.remotejobs103.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdk.remotejobs103.model.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveJob(job: Job)

    @Query("SELECT * FROM Job ORDER BY roomId DESC")
    fun getFavoritesJobs(): Flow<List<Job>>

    @Delete
    suspend fun deleteJob(job: Job)
}