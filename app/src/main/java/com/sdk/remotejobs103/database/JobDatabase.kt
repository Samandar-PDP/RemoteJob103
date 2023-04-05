package com.sdk.remotejobs103.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sdk.remotejobs103.model.Job

@Database(entities = [Job::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {
    abstract val dao: JobDao

    companion object {
        private var instance: JobDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): JobDatabase {
            return Room.databaseBuilder(
                context,
                JobDatabase::class.java,
                "job.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}