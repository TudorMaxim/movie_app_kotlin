package com.ubb.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubb.movieapp.model.Movie
import kotlinx.coroutines.CoroutineScope


@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: MovieDatabase? = null
        private var DB_NAME = "movie_android_db.db"

        fun getDatabase(context: Context, scope: CoroutineScope): MovieDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DB_NAME
                )
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}