package com.ubb.movieapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ubb.movieapp.model.Movie
import kotlinx.android.synthetic.main.movie_detail.view.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailFragment : Fragment() {
    var item: Movie? = null
    private lateinit var details: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            details = it.getStringArray("details") as Array<String>
            item = Movie(
                details[0].toInt(),
                details[1],
                details[2],
                details[3],
                details[4].toFloat()
            )
            activity?.toolbar_layout?.title = item?.name
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)
        item?.let {
            rootView.movie_genre.text = it.genre
            rootView.movie_type.text = it.type
            rootView.movie_priority.setRating(it.priority)
            rootView.movie_priority.setFocusable(false)
        }
        return rootView
    }

    companion object {
        const val ID_EXTRA = "movie_id"
        const val NAME_EXTRA = "movie_name"
        const val GENRE_EXTRA = "movie_genre"
        const val TYPE_EXTRA = "movie_type"
        const val PRIORITY_EXTRA = "priority_extra"
    }
}