package com.ubb.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ubb.movieapp.adapters.MoviesRecyclerViewAdapter
import com.ubb.movieapp.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Your Wish List"
        fab.setOnClickListener { view ->
            val intent = Intent(view.context, AddMovieActivity::class.java)
            view.context.startActivity(intent)
        }
        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = MoviesRecyclerViewAdapter(DummyContent.ITEMS)
        item_list.adapter?.notifyDataSetChanged()
    }
}