package com.najed.top10downloaderapp

import com.najed.top10downloaderapp.model.Feed
import retrofit2.Call
import retrofit2.http.GET

interface FeedAPIInterface {

    @get:GET("WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
    val feed: Call<Feed?>?

    companion object {
        const val BASE_URL = "http://ax.itunes.apple.com/"
    }
}