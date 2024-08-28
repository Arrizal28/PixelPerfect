package com.xyvona.pixelperfect.presentation.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xyvona.pixelperfect.R
import com.xyvona.pixelperfect.data.remote.dto.UnsplashImageDto
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import com.xyvona.pixelperfect.presentation.component.ImageCard
import com.xyvona.pixelperfect.presentation.component.ImageVerticalGrid
import com.xyvona.pixelperfect.presentation.component.PixPerfAppBar
import com.xyvona.pixelperfect.presentation.component.ZoomedImageCard
import com.xyvona.pixelperfect.presentation.util.SnackbarEvent
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    scrollBehavior: TopAppBarScrollBehavior,
    images: List<UnsplashImage>,
    onImageClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onFABClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(message = event.message, duration = event.duration)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PixPerfAppBar(
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
//            ImageVerticalGrid(
//                images = images,
//                onImageClick = onImageClick,
//                onImageDragStart = { image ->
//                    activeImage = image
//                    showImagePreview = true
//                },
//                onImageDragEnd = { showImagePreview = false }
//            )
        }
        FloatingActionButton(
            modifier = modifier.align(Alignment.BottomEnd).padding(24.dp),
            onClick = onFABClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Save",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        ZoomedImageCard(
            modifier = Modifier.padding(20.dp),
            isVisible = showImagePreview,
            image = activeImage
        )
    }

}
