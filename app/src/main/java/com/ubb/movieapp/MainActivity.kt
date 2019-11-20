package com.ubb.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.ubb.movieapp.adapters.MoviesRecyclerViewAdapter
import com.ubb.movieapp.database.MovieDatabase
import com.ubb.movieapp.dummy.DummyContent
import com.ubb.movieapp.model.Movie
import com.ubb.movieapp.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val addMovieActivityRequestCode = 1
    private val deleteMovieRequestCode = 2
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Your Wish List"

        val moviesAdapter = MoviesRecyclerViewAdapter(this)
        item_list.adapter = moviesAdapter
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies?.let { moviesAdapter.setMovies(it) }
        })

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddMovieActivity::class.java)
            startActivityForResult(intent, addMovieActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == addMovieActivityRequestCode && resultCode == Activity.RESULT_OK) {
            addMovie(intentData)
        } else if (deleteMovieRequestCode == requestCode && resultCode == Activity.RESULT_OK) {
            intentData.let { data ->
                val id: Int = data!!.getStringExtra(MovieDetailFragment.ID_EXTRA)!!.toInt()
                for (movie in movieViewModel.allMovies.value!!) {
                    if (movie.id == id) {
                        movieViewModel.delete(movie)
                        Unit
                    }
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Could not save!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun addMovie(intentData: Intent?) {
        intentData?.let { data ->
            val movie = Movie(
                data.getIntExtra(AddMovieActivity.ID_EXTRA, 0),
                data.getStringExtra(AddMovieActivity.NAME_EXTRA) as String,
                data.getStringExtra(AddMovieActivity.GENRE_EXTRA) as String,
                data.getStringExtra(AddMovieActivity.TYPE_EXTRA) as String,
                data.getFloatExtra(AddMovieActivity.PRIORITY_EXTRA, 0F)
            )
            movieViewModel.insert(movie)
            Unit
        }
    }

}