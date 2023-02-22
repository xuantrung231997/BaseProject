package com.example.core.network

import com.example.core.BuildConfig
import com.example.core.model.network.movie.ActorsResult
import com.example.core.model.network.movie.MovieDetailResult
import com.example.core.model.network.movie.MovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApiInterface {
    @GET("now_playing")
    suspend fun getNowPlaying(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Long,
        @Query("api_key") clientId: String = BuildConfig.MOVIE_DB_ACCESS_KEY
    ): MovieResult

    @GET("popular")
    suspend fun getPopular(
        @Query("api_key") clientId: String = BuildConfig.MOVIE_DB_ACCESS_KEY
    ): MovieResult

    @GET("/3/movie/{movieID}")
    suspend fun getMovieDetail(
        @Path("movieID") movieID: Long,
        @Query("api_key") clientId: String = BuildConfig.MOVIE_DB_ACCESS_KEY
    ): MovieDetailResult

    @GET("/3/movie/{movieID}/credits")
    suspend fun getActors(
        @Path("movieID") movieID: Long,
        @Query("api_key") clientId: String = BuildConfig.MOVIE_DB_ACCESS_KEY
    ): ActorsResult
}
