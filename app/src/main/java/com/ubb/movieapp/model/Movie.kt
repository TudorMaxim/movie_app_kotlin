package com.ubb.movieapp.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "priority") val priority: Float
)