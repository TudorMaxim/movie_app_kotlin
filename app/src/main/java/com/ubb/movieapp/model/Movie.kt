package com.ubb.movieapp.model

class Movie(val id:String, val name:String, val genre:String, val type:String, val priority:String) {
    override fun toString(): String = name
}