package com.ubb.movieapp.repository

import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.service.MovieApi

object NetworkRepository {
    suspend fun getMovies(): List<Movie> {
        var movies: ArrayList <Movie> = ArrayList()
        val response = MovieApi.service.getMovies()
        val movieList = response["movies"] ?: emptyList()
        for (movie in movieList) {
            movies.add(
                Movie(
                    movie.get("id").toString().split(".")[0].toInt(),
                    movie["name"] ?: "",
                    movie["genre"] ?: "",
                    movie["type"] ?: "",
                    movie.get("priority").toString().toFloat()
                )
            )
        }
        return movies
    }
}