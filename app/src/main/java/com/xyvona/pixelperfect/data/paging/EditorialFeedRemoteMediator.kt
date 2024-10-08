package com.xyvona.pixelperfect.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.xyvona.pixelperfect.data.local.PixPerfectDatabase
import com.xyvona.pixelperfect.data.local.entity.UnsplashImageEntity
import com.xyvona.pixelperfect.data.local.entity.UnsplashRemoteKeys
import com.xyvona.pixelperfect.data.mapper.toEntityList
import com.xyvona.pixelperfect.data.remote.UnsplashApiService
import com.xyvona.pixelperfect.data.util.Constants
import com.xyvona.pixelperfect.data.util.Constants.ITEMS_PER_PAGE

@OptIn(ExperimentalPagingApi::class)
class EditorialFeedRemoteMediator(
    private val apiService: UnsplashApiService,
    private val database: PixPerfectDatabase
) : RemoteMediator<Int, UnsplashImageEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private val editorialFeedDAO = database.editorialFeedDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageEntity>
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    Log.d(Constants.IV_LOG_TAG, "remoteKeysPrev: ${remoteKeys?.prevPage}")
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    Log.d(Constants.IV_LOG_TAG, "remoteKeysNext: ${remoteKeys?.nextPage}")
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response =
                apiService.getEditorialFeedImages(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()
            Log.d(Constants.IV_LOG_TAG, "endOfPaginationReached: $endOfPaginationReached")

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            Log.d(Constants.IV_LOG_TAG, "prevPage: $prevPage")
            Log.d(Constants.IV_LOG_TAG, "nextPage: $nextPage")

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    editorialFeedDAO.deleteAllEditorialFeedImages()
                    editorialFeedDAO.deleteAllRemoteKeys()
                }
                val remoteKeys = response.map { unsplashImageDto ->
                    UnsplashRemoteKeys(
                        id = unsplashImageDto.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                editorialFeedDAO.insertAllRemoteKeys(remoteKeys)
                editorialFeedDAO.insertEditorialFeedImages(response.toEntityList())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d(Constants.IV_LOG_TAG, "LoadResultError: ${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                editorialFeedDAO.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                editorialFeedDAO.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                editorialFeedDAO.getRemoteKeys(id = unsplashImage.id)
            }
    }
}