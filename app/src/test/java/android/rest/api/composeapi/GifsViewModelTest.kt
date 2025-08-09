package android.rest.api.composeapi


import android.rest.api.composeapi.fake.FakeDataSource
import android.rest.api.composeapi.fake.FakeNetworkGifsRepository
import android.rest.api.composeapi.rules.TestDispatcherRule

import android.rest.api.composeapi.ui.theme.screens.GifsUiState
import android.rest.api.composeapi.ui.theme.screens.GifsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
class GifsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun gifsViewModel_getGifs_verifyGifsUiStateSuccess() =
        runTest {
            val gifsViewModel = GifsViewModel(
                FakeNetworkGifsRepository()
            )
            assertEquals(
                GifsUiState.Success(FakeDataSource.gifsList),
                gifsViewModel.gifsUiState
            )
            assertEquals(
                GifsUiState.Success(FakeDataSource.gifsList),
                gifsViewModel.getStickers()
            )
        }
}