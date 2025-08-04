package android.rest.api.composeapi.fake

import android.rest.api.composeapi.model.GifResponse
import android.rest.api.composeapi.network.GifApiService

class FakeGifsApiService: GifApiService {
    override suspend fun getTrendingGifs(
        apiKey: String,
        limit: Int
    ): GifResponse {
        return FakeDataSource.fakeGifResponse
    }
}