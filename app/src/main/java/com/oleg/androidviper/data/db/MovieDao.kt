package com.oleg.androidviper.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oleg.androidviper.data.entity.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("select * from movies")
    fun getAll(): LiveData<List<Movie>>

    @Query("delete from movies where watched = :watched")
    fun deleteMovies(watched: Boolean)

    @Update
    fun updateMovie(movie: Movie)

    @Query("DELETE FROM movies WHERE id = :id")
    fun delete(id: Int?)
}
