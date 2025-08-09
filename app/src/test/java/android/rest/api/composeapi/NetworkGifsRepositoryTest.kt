package android.rest.api.composeapi

import android.rest.api.composeapi.data.NetworkGifsRepository
import android.rest.api.composeapi.fake.FakeDataSource
import android.rest.api.composeapi.fake.FakeGifsApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkGifsRepositoryTest {

    @Test
    fun networkGifsRepository_getGifs_verifyGifsList() =
        runTest {
            val repository = NetworkGifsRepository(
                gifApiService = FakeGifsApiService()
            )
            assertEquals(FakeDataSource.fakeGifResponse.gifs, repository.getGifs().gifs)
            assertEquals(FakeDataSource.fakeGifResponse.gifs, repository.getStickers().gifs)

        }
}