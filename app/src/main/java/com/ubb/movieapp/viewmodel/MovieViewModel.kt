package com.ubb.movieapp.viewmodel

import android.app.Application
import android.os.Build
import android.system.ErrnoException
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubb.movieapp.database.MovieDatabase
import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.repository.MovieRepository
import com.ubb.movieapp.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository
    private var dbMovies: LiveData<List<Movie>>
    var allMovies = MutableLiveData<List <Movie>>().apply { value = emptyList() }

    init {
        val movieDao = MovieDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao)
        dbMovies = repository.allMovies
    }

    fun loadMovies(connected: Boolean) = viewModelScope.launch {
        if (connected) {
            try {
                allMovies.value = fetchMoviesFromServer()
            } catch (error: Exception) {
                allMovies.value = dbMovies.value
            }
        } else {
            allMovies.value = dbMovies.value
        }
    }

    private suspend fun fetchMoviesFromServer() = withContext(Dispatchers.IO) {
        NetworkRepository.getMovies()
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.delete(movie)
    }

    fun update(movie: Movie) = viewModelScope.launch {
        repository.update(movie)
    }
}