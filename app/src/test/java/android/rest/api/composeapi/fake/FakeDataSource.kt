package android.rest.api.composeapi.fake

import android.rest.api.composeapi.model.GifItem
import android.rest.api.composeapi.model.OriginalImage
import android.rest.api.composeapi.model.GifResponse
import android.rest.api.composeapi.model.Images

object FakeDataSource {

    private const val gifUrlOne = "https://example.com/gif1.gif"
    private const val gifUrlTwo = "https://example.com/gif2.gif"

    val gifsList = listOf(
        GifItem(
            images = Images(
                original = OriginalImage(url = gifUrlOne)
            )
        ),
        GifItem(
            images = Images(
                original = OriginalImage(url = gifUrlTwo)
            )
        )
    )

    val fakeGifResponse = GifResponse(gifs = gifsList)
}
