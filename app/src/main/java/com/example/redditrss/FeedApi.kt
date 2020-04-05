package com.example.redditrss

import com.example.redditrss.model.Feed
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedApi {

    @GET("{feed}/.rss")
    fun loadFeed(@Path("feed") feed:String):Call<Feed>

    @GET("earthporn/.rss")
    fun getFeed(): Call<Feed>


}