package com.ubb.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import com.ubb.movieapp.dummy.DummyContent
import com.ubb.movieapp.model.Movie
import kotlinx.android.synthetic.main.movie_detail.view.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailFragment : Fragment() {
    var item: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_MOVIE_ID)) {
//                item = DummyContent.ITEM_MAP[it.getString(ARG_MOVIE_ID)]
                activity?.toolbar_layout?.title = item?.name
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)
        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.movie_genre.text = it.genre
            rootView.movie_type.text = it.type
            rootView.movie_priority.setRating(it.priority.toFloat())
            rootView.movie_priority.setFocusable(false)
        }
        return rootView
    }

    companion object {
        const val ARG_MOVIE_ID = "video_id"
    }
}