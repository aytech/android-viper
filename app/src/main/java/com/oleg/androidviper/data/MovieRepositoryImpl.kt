package com.oleg.androidviper.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oleg.androidviper.data.db.MovieDao
import com.oleg.androidviper.data.entity.Movie
import com.oleg.androidviper.data.entity.MoviesResponse
import com.oleg.androidviper.data.net.RetrofitClient
import com.oleg.androidviper.db
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.concurrent.thread

class MovieRepositoryImpl : MovieRepository {

    private val movieDao: MovieDao = db.movieDao()
    private val retrofitClient = RetrofitClient()

    override fun getSavedMovies(): LiveData<List<Movie>> {
        return movieDao.getAll()
    }

    override fun saveMovie(movie: Movie) {
        thread { movieDao.insert(movie) }
    }

    override fun deleteMovie(movie: Movie) {
        thread { movieDao.delete(movie.id) }
    }

    override fun searchMovies(query: String): LiveData<List<Movie>?> {
        val data = MutableLiveData<List<Movie>>()

        retrofitClient.searchMovies(query = query).enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                data.value = response.body()?.results
                Timber.d("Got movies: %s", response)
            }

            override fun onFailure(call: Call<MoviesResponse>, error: Throwable) {
                data.value = null
                Timber.d("Error: $error")
            }
        })

        return data
    }

}
