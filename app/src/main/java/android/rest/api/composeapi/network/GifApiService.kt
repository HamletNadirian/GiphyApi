package android.rest.api.composeapi.network

import android.rest.api.composeapi.model.GifResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GifApiService {
    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ): GifResponse
}