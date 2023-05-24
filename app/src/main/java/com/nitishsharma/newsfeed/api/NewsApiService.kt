package com.nitishsharma.newsfeed.api

import com.nitishsharma.newsfeed.api.models.News
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiService {
    @GET("v1/api.json?rss_url=http://www.abc.net.au/news/feed/51120/rss.xml")
    suspend fun getNewNews(
    ): Response<News>
}