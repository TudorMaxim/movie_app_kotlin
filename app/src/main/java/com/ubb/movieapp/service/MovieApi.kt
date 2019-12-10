package com.ubb.movieapp.service

import com.google.gson.GsonBuilder
import com.ubb.movieapp.model.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object MovieApi {
    private const val URL = "http://10.0.2.2:3000"

    interface Service {
        @GET("/movies")
        suspend fun getMovies(): Map <String, List<Map <String, String> > >

        @POST("/movies")
        suspend fun createMovie(@Body movie: Movie)

        @DELETE("/movies/{id}")
        suspend fun deleteMovie(@Path("id") id: Int)

        @PATCH("/movies/{id}")
        suspend fun updateMovie(@Path("id") id: Int, @Body movie: Movie)
    }

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()


    private var gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
}