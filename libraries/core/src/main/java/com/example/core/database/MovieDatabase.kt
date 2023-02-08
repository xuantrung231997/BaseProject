package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.MovieDao
import com.example.core.model.network.movie.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}
