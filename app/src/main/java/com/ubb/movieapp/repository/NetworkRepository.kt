package com.ubb.movieapp.repository

import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.service.MovieApi

object NetworkRepository {
    suspend fun getMovies(): List<Movie> {
        val response = MovieApi.service.getMovies()
        return this.getMovieList(response)
    }

    suspend fun createMovie(movie: Movie): List <Movie> {
        val response = MovieApi.service.createMovie(movie)
        return this.getMovieList(response)
    }

    suspend fun deleteMovie(movieId: Int): List <Movie> {
        val response = MovieApi.service.deleteMovie(movieId)
        return this.getMovieList(response)
    }

    suspend fun updateMovie(movie: Movie): List <Movie> {
        val response = MovieApi.service.updateMovie(movie.id, movie)
        return this.getMovieList(response)
    }

    suspend fun syncMovies(movies: List <Movie>): List <Movie> {
        val response = MovieApi.service.syncMovies(movies)
        return this.getMovieList(response)
    }

    private fun getMovieList(response: Map <String, List<Map <String, String> > > ): ArrayList<Movie> {
        val movies: ArrayList <Movie> = ArrayList()
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