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

    init {
        getGifs()
    }

    fun getGifs() {
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