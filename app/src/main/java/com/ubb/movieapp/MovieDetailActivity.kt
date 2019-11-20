package com.ubb.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NavUtils
import com.google.android.material.snackbar.Snackbar
import com.ubb.movieapp.dummy.DummyContent
import com.ubb.movieapp.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        MovieDetailFragment.ARG_MOVIE_ID,
                        intent.getStringExtra(MovieDetailFragment.ARG_MOVIE_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit()
        }

        delete_button.setOnClickListener {
            val id = intent.getStringExtra(MovieDetailFragment.ARG_MOVIE_ID) as String
//            DummyContent.deleteMovie(id)
            NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
        }

        update_button.setOnClickListener {view ->
            val id = intent.getStringExtra(MovieDetailFragment.ARG_MOVIE_ID) as String
            var intent = Intent(view.context, AddMovieActivity::class.java).apply {
                this.putExtra(MovieDetailFragment.ARG_MOVIE_ID, id)
            }
            view.context.startActivity(intent)
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
