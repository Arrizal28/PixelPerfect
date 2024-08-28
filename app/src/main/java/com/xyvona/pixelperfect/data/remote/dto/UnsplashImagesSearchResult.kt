package com.xyvona.pixelperfect.data.remote.dto

import com.xyvona.pixelperfect.domain.model.UnsplashImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImagesSearchResult(
    @SerialName("results")
    val images: List<UnsplashImageDto>,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)