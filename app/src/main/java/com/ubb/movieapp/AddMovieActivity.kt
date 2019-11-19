package com.ubb.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.NavUtils
import com.google.android.material.snackbar.Snackbar
import com.ubb.movieapp.dummy.DummyContent
import com.ubb.movieapp.model.Movie
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.add_movie_form.*

class AddMovieActivity : AppCompatActivity() {
    private var movie:Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setSupportActionBar(add_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra(MovieDetailFragment.ARG_MOVIE_ID)
        if (id != null) {
            movie = DummyContent.getMovie(id)
        }
        if (movie == null) {
            // add operation
            supportActionBar?.title = "Add a movie"
            input_priority.setRating(3.0f)
        } else {
            // update operation
            supportActionBar?.title = "Update a movie"
            input_name.setText(movie?.name)
            input_genre.setText(movie?.genre)
            input_type.setText(movie?.type)
            val priority: String = movie?.priority as String
            input_priority.setRating(priority.toFloat())
        }
    }

    fun submitAddForm(view: View) {
        val movieName = input_name.text.toString()
        val movieType = input_type.text.toString()
        val movieGenre = input_genre.text.toString()
        val moviePriority = input_priority.getRating()

        if (movieName == "" || movieType == "" || movieGenre == "" || moviePriority == 0.0f) {
            Snackbar
                .make(view, "Please fill in all the fields!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        } else {
            if (movie == null) {
                // add
                val id = DummyContent.ITEMS.map {movie -> movie.id }.max() + 1
                DummyContent.addMovie(Movie(id, movieName, movieGenre, movieType, moviePriority.toString()))
            } else {
                // update
                val newMovie = Movie(movie?.id as String, movieName, movieGenre, movieType, moviePriority.toString())
                DummyContent.updateMovie(movie?.id as String, newMovie)
            }
            NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
