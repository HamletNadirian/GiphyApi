package android.rest.api.composeapi.data

import android.rest.api.composeapi.network.GifApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val imagesGifsRepository: GifsRepository
}
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.giphy.com/"
    private val json = Json { ignoreUnknownKeys = true } // Add ignoreUnknownKeys = true
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GifApiService by lazy {
        retrofit.create(GifApiService::class.java)
    }
    override val imagesGifsRepository: GifsRepository by lazy {
        NetworkGifsRepository(retrofitService)
    }
}