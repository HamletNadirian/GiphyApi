package android.rest.api.composeapi.fake


import android.rest.api.composeapi.data.GifsRepository
import android.rest.api.composeapi.model.GifResponse

class FakeNetworkGifsRepository : GifsRepository {
    override suspend fun getGifs(): GifResponse {
        return FakeDataSource.fakeGifResponse
    }
}
