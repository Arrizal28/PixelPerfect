package com.xyvona.pixelperfect.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.xyvona.pixelperfect.data.local.PixPerfectDatabase
import com.xyvona.pixelperfect.data.mapper.toDomainModel
import com.xyvona.pixelperfect.data.mapper.toDomainModelList
import com.xyvona.pixelperfect.data.mapper.toFavoriteImageEntity
import com.xyvona.pixelperfect.data.paging.EditorialFeedRemoteMediator
import com.xyvona.pixelperfect.data.paging.SearchPagingSource
import com.xyvona.pixelperfect.data.remote.UnsplashApiService
import com.xyvona.pixelperfect.data.util.Constants.ITEMS_PER_PAGE
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import com.xyvona.pixelperfect.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService,
    private val database: PixPerfectDatabase
): ImageRepository {

    private val favoriteImagesDao = database.favoriteImagesDAO()
    private val editorialImagesDao = database.editorialFeedDAO()

    override fun getEditorialFeedImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            remoteMediator = EditorialFeedRemoteMediator(
                unsplashApi,
                database
            ),
            pagingSourceFactory = {
                editorialImagesDao.getAllEditorialFeedImages()
            }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }

    override fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    query,
                    unsplashApi
                )
            }
        ).flow
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage) {
        val isFavorite = favoriteImagesDao.isImageFavorite(image.id)
        val favoriteImage = image.toFavoriteImageEntity()

        if (isFavorite) {
            favoriteImagesDao.deleteFavoriteImage(favoriteImage)
        } else {
            favoriteImagesDao.insertFavoriteImage(favoriteImage)
        }
    }

    override fun getFavoriteImageIds(): Flow<List<String>> {
        return favoriteImagesDao.getFavoriteImageIds()
    }

    override fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                favoriteImagesDao.getAllFavoriteImages()
            }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }
}