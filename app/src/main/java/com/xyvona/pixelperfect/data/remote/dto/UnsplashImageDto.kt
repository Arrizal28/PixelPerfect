package com.xyvona.pixelperfect.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDto(
    val id: String,
    val description: String?,
    val height: Int,
    val width: Int,
    val urls: UrlsDto,
    val user: User
)

@Serializable
data class UrlsDto(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)