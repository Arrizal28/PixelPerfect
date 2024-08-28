package com.xyvona.pixelperfect.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.xyvona.pixelperfect.data.mapper.toDomainModel
import com.xyvona.pixelperfect.data.mapper.toDomainModelList
import com.xyvona.pixelperfect.data.paging.SearchPagingSource
import com.xyvona.pixelperfect.data.remote.UnsplashApiService
import com.xyvona.pixelperfect.data.util.Constants.ITEMS_PER_PAGE
import com.xyvona.pixelperfect.domain.model.UnsplashImage
import com.xyvona.pixelperfect.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService
): ImageRepository {

    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
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
}