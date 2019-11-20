package com.ubb.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.app.NavUtils
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
                    putStringArray("details", arrayOf(
                        intent.getStringExtra(MovieDetailFragment.ID_EXTRA),
                        intent.getStringExtra(MovieDetailFragment.NAME_EXTRA),
                        intent.getStringExtra(MovieDetailFragment.GENRE_EXTRA),
                        intent.getStringExtra(MovieDetailFragment.TYPE_EXTRA),
                        intent.getStringExtra(MovieDetailFragment.PRIORITY_EXTRA)
                    ))
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit()
        }

        val id = intent.getStringExtra(MovieDetailFragment.ID_EXTRA)
        val name = intent.getStringExtra(MovieDetailFragment.NAME_EXTRA)
        val genre = intent.getStringExtra(MovieDetailFragment.GENRE_EXTRA)
        val type = intent.getStringExtra(MovieDetailFragment.TYPE_EXTRA)
        val priority = intent.getStringExtra(MovieDetailFragment.PRIORITY_EXTRA)

        delete_button.setOnClickListener {
            val intent = Intent()
            intent.putExtra(MovieDetailFragment.ID_EXTRA, id)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        update_button.setOnClickListener {view ->
            val intent = Intent(this, AddMovieActivity::class.java)
            intent.let {
                it.putExtra(MovieDetailFragment.ID_EXTRA, id)
                it.putExtra(MovieDetailFragment.NAME_EXTRA, name)
                it.putExtra(MovieDetailFragment.GENRE_EXTRA, genre)
                it.putExtra(MovieDetailFragment.TYPE_EXTRA, type)
                it.putExtra(MovieDetailFragment.PRIORITY_EXTRA, priority)
            }
            (view.context as Activity).startActivityForResult(intent, 3)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            data.let { intent ->
                val replyIntent = Intent()
                val params: ArrayList<String> = arrayListOf(
                    intent?.getStringExtra(AddMovieActivity.ID_EXTRA) as String,
                    intent.getStringExtra(AddMovieActivity.NAME_EXTRA) as String,
                    intent.getStringExtra(AddMovieActivity.GENRE_EXTRA) as String,
                    intent.getStringExtra(AddMovieActivity.TYPE_EXTRA) as String,
                    intent.getStringExtra(AddMovieActivity.PRIORITY_EXTRA) as String
                )
                replyIntent.putStringArrayListExtra("params", params)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
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
