package com.xyvona.pixelperfect.domain.repository

import androidx.paging.PagingData
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getEditorialFeedImages(): Flow<PagingData<UnsplashImage>>

    suspend fun getImage(imageId: String): UnsplashImage

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>

    suspend fun toggleFavoriteStatus(image: UnsplashImage)

    fun getFavoriteImageIds(): Flow<List<String>>

    fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>>
}