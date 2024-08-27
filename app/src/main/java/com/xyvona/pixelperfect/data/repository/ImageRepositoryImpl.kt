package com.xyvona.pixelperfect.data.repository

import com.xyvona.pixelperfect.data.mapper.toDomainModel
import com.xyvona.pixelperfect.data.mapper.toDomainModelList
import com.xyvona.pixelperfect.data.remote.UnsplashApiService
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import com.xyvona.pixelperfect.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService
): ImageRepository {

    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }
}