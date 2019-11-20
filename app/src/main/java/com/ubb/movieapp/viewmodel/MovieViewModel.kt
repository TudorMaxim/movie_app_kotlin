package com.ubb.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ubb.movieapp.database.MovieDatabase
import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository
    val allMovies: LiveData<List<Movie>>
    init {
        val movieDao = MovieDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao)
        allMovies = repository.allMovies
    }
    /**
     * ViewModels have a coroutine scope based on their lifecycle called viewModelScope which we can use here.
     * We do not want to make the query on the UI thread
     */
    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }
}