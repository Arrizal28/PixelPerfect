package com.xyvona.pixelperfect.domain.repository

import androidx.paging.PagingData
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun getEditorialFeedImages(): List<UnsplashImage>

    suspend fun getImage(imageId: String): UnsplashImage

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>
}