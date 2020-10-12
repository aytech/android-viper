package com.oleg.androidviper.data.net

import com.oleg.androidviper.data.entity.MoviesResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val moviesApi: MoviesApi

    companion object {
        private const val API_KEY = ""
        private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    init {
        val okHttpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder().baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    fun searchMovies(query: String): Call<MoviesResponse> {
        return moviesApi.searchMovie(API_KEY, query)
    }
}