package com.ubb.movieapp.viewmodel

import android.app.Application
import android.util.Log
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
    var serverMovies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    var allMovies: LiveData<List<Movie>> = serverMovies

    init {
        val movieDao = MovieDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao)
        allMovies = repository.allMovies
    }

    fun loadMoviesOffline() = viewModelScope.launch {
        allMovies = repository.allMovies
    }

    fun loadMoviesOnline() = viewModelScope.launch {
        try {
            serverMovies.value = fetchMoviesFromServer()
            allMovies = serverMovies
        } catch (error: Exception) {
            Log.d("LOAD ERROR", "COULD NOT FETCH MOVIES FROM SERVER")
        }
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun insertOnline(movie: Movie) = viewModelScope.launch {
        insertMovieServer(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.delete(movie)
        deleteMovieServer(movie)
    }

    fun update(movie: Movie) = viewModelScope.launch {
        repository.update(movie)
        updateMovieServer(movie)
    }

    fun syncMovies() = viewModelScope.launch {
        val movies: ArrayList<Movie> = ArrayList()
        allMovies.value?.forEach { movie ->
            movies.add(movie)
        }
        synchronizeMovies(movies)
    }

    private suspend fun fetchMoviesFromServer() = withContext(Dispatchers.IO) {
        NetworkRepository.getMovies()
    }

    private suspend fun insertMovieServer(movie: Movie) = withContext(Dispatchers.IO) {
        NetworkRepository.createMovie(movie)
    }

    private suspend fun deleteMovieServer(movie: Movie) = withContext(Dispatchers.IO) {
        NetworkRepository.deleteMovie(movie.id)
    }

    private suspend fun updateMovieServer(movie: Movie) = withContext(Dispatchers.IO) {
        NetworkRepository.updateMovie(movie)
    }

    private suspend fun synchronizeMovies(movies: List <Movie>) = withContext(Dispatchers.IO) {
        NetworkRepository.syncMovies(movies)
    }

}