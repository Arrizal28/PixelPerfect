package com.xyvona.pixelperfect.domain.repository

import com.xyvona.pixelperfect.domain.model.UnsplashImage

interface ImageRepository {

    suspend fun getEditorialFeedImages(): List<UnsplashImage>

    suspend fun getImage(imageId: String): UnsplashImage
}