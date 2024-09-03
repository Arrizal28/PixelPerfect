package com.xyvona.pixelperfect.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xyvona.pixelperfect.data.local.entity.FavoriteImageEntity
import com.xyvona.pixelperfect.data.local.entity.UnsplashImageEntity
import com.xyvona.pixelperfect.data.local.entity.UnsplashRemoteKeys

@Database(
    entities = [FavoriteImageEntity::class, UnsplashImageEntity::class, UnsplashRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class PixPerfectDatabase: RoomDatabase() {
    abstract fun favoriteImagesDAO(): FavoriteImagesDAO

    abstract fun editorialFeedDAO(): EditorialFeedDAO
}