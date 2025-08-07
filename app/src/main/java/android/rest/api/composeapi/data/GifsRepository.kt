package android.rest.api.composeapi.data

import android.rest.api.composeapi.model.GifResponse
import android.rest.api.composeapi.network.GifApiService

interface GifsRepository {
    suspend fun getGifs():  GifResponse
}

class NetworkGifsRepository(
    private val gifApiService: GifApiService
) : GifsRepository {
    override suspend fun getGifs(): GifResponse
    = gifApiService.getTrendingGifs("KEY",15)
}