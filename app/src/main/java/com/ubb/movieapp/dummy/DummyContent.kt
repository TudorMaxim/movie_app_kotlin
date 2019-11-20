package com.ubb.movieapp.dummy
import com.ubb.movieapp.model.Movie

object DummyContent {
//    val ITEMS: MutableList<Movie> = ArrayList()
//    val ITEM_MAP: MutableMap<String, Movie> = HashMap()
//    private const val COUNT = 10
//
//    init {
//        for (i in 1..COUNT) {
//            addMovie(createMovie(i))
//        }
//        ITEMS.sortByDescending { movie -> movie.priority.toFloat() }
//    }
//
//    fun addMovie(item: Movie) {
//        ITEMS.add(item)
//        ITEMS.sortByDescending { movie -> movie.priority.toFloat() }
//        ITEM_MAP[item.id] = item
//    }
//
//    fun deleteMovie(id: String) {
//        var index = -1
//        for (i in ITEMS.indices) {
//            if (ITEMS[i].id == id) {
//                index = i
//            }
//        }
//        ITEMS.removeAt(index)
//        ITEM_MAP.remove(id)
//    }
//
//    fun getMovie(id: String): Movie? {
//        var movie: Movie?
//        movie = ITEMS.filter { m -> m.id == id }[0]
//        return movie
//    }
//
//    fun updateMovie(id: String, newMovie: Movie) {
//        var index = -1
//        for (i in ITEMS.indices) {
//            if (ITEMS[i].id == id) {
//                index = i
//            }
//        }
//        ITEMS[index] = newMovie
//        ITEMS.sortByDescending { movie -> movie.priority.toFloat() }
//        ITEM_MAP[id] = newMovie
//    }
//
//    private fun createMovie(position: Int): Movie {
//        return Movie(position.toString(), "Movie $position", "Action", "Movie", "3")
//    }
}