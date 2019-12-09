package com.ubb.movieapp.repository

import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.service.MovieApi

object NetworkRepository {
    suspend fun getMovies(): List<Movie> {
        return getMoviesRequest()["movies"] ?: emptyList()
    }

    private suspend fun getMoviesRequest(): Map <String, List<Movie> > = MovieApi.service.getMovies()

}