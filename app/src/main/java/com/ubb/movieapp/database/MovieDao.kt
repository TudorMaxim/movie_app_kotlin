package com.ubb.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ubb.movieapp.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY priority DESC")
    fun getAll(): LiveData<List <Movie>>

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg movies: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Update
    suspend fun update(vararg movies: Movie)
}