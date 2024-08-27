package com.xyvona.pixelperfect.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xyvona.pixelperfect.data.mapper.toDomainModelList
import com.xyvona.pixelperfect.data.remote.dto.UnsplashImageDto
import com.xyvona.pixelperfect.di.AppModule
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import com.xyvona.pixelperfect.domain.repository.ImageRepository
import com.xyvona.pixelperfect.presentation.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {

    var images: List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch {
            try {
                val result = repository.getEditorialFeedImages()
                images = result
            } catch (e: UnknownHostException) {
                _snackbarEvent.send(SnackbarEvent(message = "No internet connection. Please Check your network"))
            } catch (e: Exception) {
                _snackbarEvent.send(SnackbarEvent(message = "Something went wrong: ${e.message}"))
            }
        }
    }
}