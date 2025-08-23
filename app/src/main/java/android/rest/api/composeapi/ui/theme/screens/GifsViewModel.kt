package android.rest.api.composeapi.ui.theme.screens

import android.rest.api.composeapi.GifsApplication
import android.rest.api.composeapi.data.GifsRepository
import android.rest.api.composeapi.model.GifItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface GifsUiState {
    data class Success(val gifs: List<GifItem>) : GifsUiState
    object Error : GifsUiState
    object Loading : GifsUiState
}

class GifsViewModel(private val imagesGifsRepository: GifsRepository) : ViewModel() {

    var gifsUiState: GifsUiState by mutableStateOf(GifsUiState.Loading)
        private set

    var stickerUiState: GifsUiState by mutableStateOf(GifsUiState.Loading)
        private set

    var searchGifsUiState: GifsUiState by mutableStateOf(GifsUiState.Loading)
        private set

    var searchStickersUiState: GifsUiState by mutableStateOf(GifsUiState.Loading)
        private set

    var currentSearchQuery: String by mutableStateOf("")
        private set

    var isSearchMode: Boolean by mutableStateOf(false)
        private set

    init {
        getGifs()
        getStickers()
       /* searchGifs("cats")
        searchStickers("cats")*/
    }

    fun getGifs() {
        isSearchMode = false

        viewModelScope.launch {
            gifsUiState = GifsUiState.Loading
            gifsUiState = try {
                GifsUiState.Success(imagesGifsRepository.getGifs().gifs)
            } catch (e: IOException) {
                GifsUiState.Error
            } catch (e: HttpException) {
                GifsUiState.Error
            }
        }
    }

    fun getStickers() {
        isSearchMode = false

        viewModelScope.launch {
            stickerUiState = GifsUiState.Loading
            stickerUiState = try {
                GifsUiState.Success(imagesGifsRepository.getStickers().gifs)
            } catch (e: IOException) {
                GifsUiState.Error
            } catch (e: HttpException) {
                GifsUiState.Error
            }
        }
    }

    fun searchGifs(query: String) {
        isSearchMode = true

        if (query.isBlank()) {
            clearSearch()
            return
        }

        currentSearchQuery = query
        isSearchMode = true

        viewModelScope.launch {
            searchGifsUiState = GifsUiState.Loading
            searchGifsUiState = try {
                GifsUiState.Success(imagesGifsRepository.searchGifs(query).gifs)
            } catch (e: IOException) {
                GifsUiState.Error
            } catch (e: HttpException) {
                GifsUiState.Error
            }
        }
    }

    fun searchStickers(query: String) {
        isSearchMode = true

        if (query.isBlank()) {
            clearSearch()
            return
        }

        currentSearchQuery = query
        isSearchMode = true

        viewModelScope.launch {
            searchStickersUiState = GifsUiState.Loading
            searchStickersUiState = try {
                GifsUiState.Success(imagesGifsRepository.searchStickers(query).gifs)
            } catch (e: IOException) {
                GifsUiState.Error
            } catch (e: HttpException) {
                GifsUiState.Error
            }
        }
    }

    fun clearSearch() {
        currentSearchQuery = ""
        isSearchMode = false
        searchGifsUiState = GifsUiState.Success(emptyList())
        searchStickersUiState = GifsUiState.Success(emptyList())
    }

    fun getCurrentGifsState(): GifsUiState {
        return if (isSearchMode) searchGifsUiState else gifsUiState
    }

    fun getCurrentStickersState(): GifsUiState {
        return if (isSearchMode) searchStickersUiState else stickerUiState
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GifsApplication)
                val imagesGifsRepository = application.container.imagesGifsRepository
                GifsViewModel(imagesGifsRepository = imagesGifsRepository)
            }
        }
    }

}