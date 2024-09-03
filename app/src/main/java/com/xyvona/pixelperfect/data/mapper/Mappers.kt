package com.xyvona.pixelperfect.data.mapper

import com.xyvona.pixelperfect.data.local.entity.FavoriteImageEntity
import com.xyvona.pixelperfect.data.local.entity.UnsplashImageEntity
import com.xyvona.pixelperfect.data.remote.dto.UnsplashImageDto
import com.xyvona.pixelperfect.domain.model.UnsplashImage

fun UnsplashImageDto.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImgUrl = this.user.profileImage.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun UnsplashImageDto.toEntity(): UnsplashImageEntity {
    return UnsplashImageEntity(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImgUrl = this.user.profileImage.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun UnsplashImage.toFavoriteImageEntity(): FavoriteImageEntity {
    return FavoriteImageEntity(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun FavoriteImageEntity.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun UnsplashImageEntity.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun List<UnsplashImageDto>.toDomainModelList(): List<UnsplashImage> {
    return this.map { it.toDomainModel() }
}

fun List<UnsplashImageDto>.toEntityList(): List<UnsplashImageEntity> {
    return this.map { it.toEntity() }
}