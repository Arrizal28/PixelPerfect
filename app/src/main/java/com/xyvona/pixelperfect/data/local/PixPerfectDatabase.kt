package com.xyvona.pixelperfect.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xyvona.pixelperfect.data.local.entity.FavoriteImageEntity

@Database(
    entities = [FavoriteImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PixPerfectDatabase: RoomDatabase() {
    abstract fun favoriteImagesDAO(): FavoriteImagesDAO


}