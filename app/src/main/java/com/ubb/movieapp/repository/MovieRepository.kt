package com.ubb.movieapp.repository

import androidx.lifecycle.LiveData
import com.ubb.movieapp.database.MovieDao
import com.ubb.movieapp.model.Movie

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: LiveData<List<Movie>> = movieDao.getAll()

    suspend fun insert(movie: Movie) {
        movieDao.insert(movie)
    }
}