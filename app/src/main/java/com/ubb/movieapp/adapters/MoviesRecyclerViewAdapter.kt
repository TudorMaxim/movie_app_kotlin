package com.ubb.movieapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ubb.movieapp.MovieDetailActivity
import com.ubb.movieapp.R
import com.ubb.movieapp.MovieDetailFragment
import com.ubb.movieapp.model.Movie
import kotlinx.android.synthetic.main.movie_list_content.view.*
import kotlin.math.log

class MoviesRecyclerViewAdapter internal constructor(context: Context): RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var movies: List<Movie> = emptyList()
    private val onClickListener: View.OnClickListener
    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Movie
            val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailFragment.ID_EXTRA, item.id.toString())
                putExtra(MovieDetailFragment.NAME_EXTRA, item.name)
                putExtra(MovieDetailFragment.GENRE_EXTRA, item.genre)
                putExtra(MovieDetailFragment.TYPE_EXTRA, item.type)
                putExtra(MovieDetailFragment.PRIORITY_EXTRA, item.priority.toString())
            }
            (context as Activity).startActivityForResult(intent, 2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.movie_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        holder.nameView.text = item.name
        holder.genreView.rating = item.priority
        holder.genreView.setFocusable(false)
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    internal fun setMovies(movies: List<Movie>) {
        this.movies = movies
        this.movies.sortedByDescending { movie -> movie.priority }
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
    
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.video_name
        val genreView: RatingBar = view.video_priority
    }
}