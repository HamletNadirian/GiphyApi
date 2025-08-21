package android.rest.api.composeapi.network

import android.rest.api.composeapi.model.GifItem
import android.rest.api.composeapi.model.GifResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GifApiService {
    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ): GifResponse

    @GET("v1/stickers/trending")
    suspend fun getTrendingStickers(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ): GifResponse

    @GET("v1/gifs/search")
    suspend fun searchTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): GifResponse
    //https://api.giphy.com/v1/gifs/search?api_key=sUQqe1ehBvfCmayEnDx7llCXBIhPnI7i&q=cat&limit=5
    @GET("v1/stickers/search")
    suspend fun searchTrendingStickers(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): GifResponse
    //https://api.giphy.com/v1/gifs/search?api_key=sUQqe1ehBvfCmayEnDx7llCXBIhPnI7i&q=cat&limit=5
}