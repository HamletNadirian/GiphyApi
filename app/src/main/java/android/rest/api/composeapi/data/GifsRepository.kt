package android.rest.api.composeapi.data

import android.rest.api.composeapi.model.GifResponse
import android.rest.api.composeapi.network.GifApiService
import android.rest.api.composeapi.BuildConfig
interface GifsRepository {
    suspend fun getGifs():  GifResponse
    suspend fun getStickers():  GifResponse
}

class NetworkGifsRepository(
    private val gifApiService: GifApiService
) : GifsRepository {

    override suspend fun getGifs(): GifResponse
    = gifApiService.getTrendingGifs(apiKey = BuildConfig.GIPHY_API_KEY,15)

    override suspend fun getStickers(): GifResponse
    = gifApiService.getTrendingStickers(apiKey = BuildConfig.GIPHY_API_KEY,15)

}