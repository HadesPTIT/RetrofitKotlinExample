package com.tutorial.smallretrofitdemo.network

import com.tutorial.smallretrofitdemo.entity.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("movie/{id}/videos")
    fun getMovieVideos(@Path("id") movieId : Int?, @Query("api_key") apiKey : String ) : Call<VideoResponse>



}