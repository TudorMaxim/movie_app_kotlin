package com.ubb.movieapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
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
            // movie = DummyContent.getMovie(id)
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
            } else {
                this.update(movie?.id as Int, movieName, movieType, movieGenre, moviePriority)
            }
            //NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun add(name: String, type:String, genre:String, priority: Float) {
        val intent = Intent()
        intent.putExtra(NAME_EXTRA, name)
        intent.putExtra(GENRE_EXTRA, genre)
        intent.putExtra(TYPE_EXTRA, type)
        intent.putExtra(PRIORITY_EXTRA, priority)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun update(id: Int, name: String, type:String, genre:String, priority: Float) {
        val intent = Intent()
        intent.putExtra(ID_EXTRA, id)
        intent.putExtra(NAME_EXTRA, name)
        intent.putExtra(GENRE_EXTRA, genre)
        intent.putExtra(TYPE_EXTRA, type)
        intent.putExtra(PRIORITY_EXTRA, priority)
        setResult(Activity.RESULT_OK, intent)
    }

    companion object {
        const val ID_EXTRA = "movie_id"
        const val NAME_EXTRA = "movie_name"
        const val GENRE_EXTRA = "movie_genre"
        const val TYPE_EXTRA = "movie_type"
        const val PRIORITY_EXTRA = "priority_extra"

    }

//    override fun onOptionsItemSelected(item: MenuItem) =
//        when (item.itemId) {
//            android.R.id.home -> {
//                NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
}
