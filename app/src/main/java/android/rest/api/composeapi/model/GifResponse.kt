package android.rest.api.composeapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifResponse(
    @SerialName("data")
    val gifs: List<GifItem>
)
@Serializable
data class GifItem(
    val images: Images
)
@Serializable
data class Images(
    val original: OriginalImage
)
@Serializable
data class OriginalImage(
    val url: String
)
