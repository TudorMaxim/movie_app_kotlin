package com.ubb.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ubb.movieapp.adapters.MoviesRecyclerViewAdapter
import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {
    private val addMovieActivityRequestCode = 1
    private val deleteAndUpdateMovieRequestCode = 2
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Your Wish List"

        val moviesAdapter = MoviesRecyclerViewAdapter(this)
        item_list.adapter = moviesAdapter

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        if (isConnected()) {
            movieViewModel.loadMoviesOnline()
        } else {
            movieViewModel.loadMoviesOffline()
        }

        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies?.let { moviesAdapter.setMovies(it) }
        })

        movieViewModel.serverMovies.observe(this, Observer { movies ->
            movies?.let { moviesAdapter.setMovies(it) }

        })

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddMovieActivity::class.java)
            startActivityForResult(intent, addMovieActivityRequestCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == addMovieActivityRequestCode && resultCode == Activity.RESULT_OK) {
            addMovie(intentData)
        } else if (deleteAndUpdateMovieRequestCode == requestCode && resultCode == Activity.RESULT_OK) {
            if (intentData?.getStringExtra(MovieDetailFragment.ID_EXTRA) != null) { // delete
                deleteMovie(intentData)
            } else { // update
                updateMovie(intentData)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Could not save!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        super.onNetworkConnectionChanged(isConnected)
        if (isConnected) {
            movieViewModel.syncMovies()
            movieViewModel.loadMoviesOnline()
        } else {
            movieViewModel.loadMoviesOffline()
        }
    }

    fun refresh(view: View?) {
        if (isConnected()) {
            movieViewModel.loadMoviesOnline()
        } else {
            movieViewModel.loadMoviesOffline()
        }
    }


    private fun addMovie(intentData: Intent?) {
        intentData?.let { data ->
            val movie = Movie(
                data.getIntExtra(MovieDetailFragment.ID_EXTRA, 0),
                data.getStringExtra(MovieDetailFragment.NAME_EXTRA) as String,
                data.getStringExtra(MovieDetailFragment.GENRE_EXTRA) as String,
                data.getStringExtra(MovieDetailFragment.TYPE_EXTRA) as String,
                data.getFloatExtra(MovieDetailFragment.PRIORITY_EXTRA, 0F)
            )
            if (isConnected()) {
                movieViewModel.insertOnline(movie)
                Unit
            } else {
                movieViewModel.insert(movie)
                Unit
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun deleteMovie(data: Intent?) {
        val id: Int = data?.getStringExtra(MovieDetailFragment.ID_EXTRA)!!.toInt()
        for (movie in movieViewModel.allMovies.value!!) {
            if (movie.id == id) {
                movieViewModel.delete(movie)
                Unit
            }
        }
    }

    private fun updateMovie(data: Intent?) {
        val params = data?.getStringArrayListExtra(MovieDetailFragment.DETAILS_EXTRA)
        val movie = Movie(
            params?.get(0)!!.toInt(),
            params[1],
            params[2],
            params[3],
            params[4].toFloat()
        )
        movieViewModel.update(movie)
    }
}