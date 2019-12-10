package com.ubb.movieapp.viewmodel

import android.app.Application
import android.os.Build
import android.system.ErrnoException
import android.util.Log
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
    private var serverMovies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    var allMovies: LiveData<List <Movie>>

    init {
        val movieDao = MovieDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao)
        allMovies = repository.allMovies
    }

    fun loadMovies(connected: Boolean) = viewModelScope.launch {
        if (connected) {
            try {
                serverMovies.value = fetchMoviesFromServer()
                allMovies = serverMovies
            } catch (error: Exception) {
                Log.d("LOAD ERROR", "COULD NOT FETCH MOVIES FROM SERVER")
            }
        } else {
            allMovies = repository.allMovies
        }
    }

    private suspend fun fetchMoviesFromServer() = withContext(Dispatchers.IO) {
        NetworkRepository.getMovies()
    }

    fun insert(movie: Movie, connected: Boolean) = viewModelScope.launch {
        repository.insert(movie)
        if (connected) {
            insertMovieServer(movie)
        }
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.delete(movie)
        deleteMovieServer(movie)
    }

    fun update(movie: Movie) = viewModelScope.launch {
        repository.update(movie)
        updateMovieServer(movie)
    }

    private suspend fun insertMovieServer(movie: Movie) = viewModelScope.launch {
        NetworkRepository.createMovie(movie)
    }

    private suspend fun deleteMovieServer(movie: Movie) = viewModelScope.launch {
        NetworkRepository.deleteMovie(movie.id)
    }

    private suspend fun updateMovieServer(movie: Movie) = viewModelScope.launch {
        NetworkRepository.updateMovie(movie)
    }
}