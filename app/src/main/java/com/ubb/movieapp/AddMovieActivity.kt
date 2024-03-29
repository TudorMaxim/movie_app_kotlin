package com.ubb.movieapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.NavUtils
import com.google.android.material.snackbar.Snackbar
import com.ubb.movieapp.model.Movie
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.add_movie_form.*

class AddMovieActivity : BaseActivity() {
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setSupportActionBar(add_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra(MovieDetailFragment.ID_EXTRA)

        if (id != null) {
            movie = Movie(
                id.toInt(),
                intent.getStringExtra(MovieDetailFragment.NAME_EXTRA)!!,
                intent.getStringExtra(MovieDetailFragment.GENRE_EXTRA)!!,
                intent.getStringExtra(MovieDetailFragment.TYPE_EXTRA)!!,
                intent.getStringExtra(MovieDetailFragment.PRIORITY_EXTRA)!!.toFloat()
            )
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
            val priority: Float = movie?.priority as Float
            input_priority.setRating(priority)
        }
    }

    fun submit(view: View) {
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
                this.add(movieName, movieType, movieGenre, moviePriority)
                finish()
            } else {
                if (isConnected()) {
                    this.update(movie?.id as Int, movieName, movieType, movieGenre, moviePriority)
                    finish()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("You cannot update this movie because you are offline!")
                        .setNegativeButton(android.R.string.no, null)
                        .show()
                }
            }
        }
    }

    private fun add(name: String, type:String, genre:String, priority: Float) {
        val intent = Intent()
        intent.putExtra(MovieDetailFragment.NAME_EXTRA, name)
        intent.putExtra(MovieDetailFragment.GENRE_EXTRA, genre)
        intent.putExtra(MovieDetailFragment.TYPE_EXTRA, type)
        intent.putExtra(MovieDetailFragment.PRIORITY_EXTRA, priority)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun update(id: Int, name: String, type:String, genre:String, priority: Float) {
        val intent = Intent()
        intent.putExtra(MovieDetailFragment.ID_EXTRA, id.toString())
        intent.putExtra(MovieDetailFragment.NAME_EXTRA, name)
        intent.putExtra(MovieDetailFragment.GENRE_EXTRA, genre)
        intent.putExtra(MovieDetailFragment.TYPE_EXTRA, type)
        intent.putExtra(MovieDetailFragment.PRIORITY_EXTRA, priority.toString())
        setResult(Activity.RESULT_OK, intent)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            val messageToUser = "You are offline"
            mSnackBar = Snackbar.make(
                findViewById(R.id.form_root),
                messageToUser,
                Snackbar.LENGTH_INDEFINITE
            ) //Assume "rootLayout" as the root layout of every activity.
            mSnackBar?.show()
        } else {
            mSnackBar?.dismiss()
        }
    }
}
