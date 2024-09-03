package com.xyvona.pixelperfect.data.util

import com.xyvona.pixelperfect.BuildConfig

object Constants {

    const val IV_LOG_TAG = "PixelPerfectLogs"


    const val API_KEY = BuildConfig.UNSPLASH_API_KEY
    const val BASE_URL = "https://api.unsplash.com/"

    const val FAVORITE_IMAGE_TABLE = "favorite_images_table"
    const val UNSPLASH_IMAGE_TABLE = "images_table"
    const val UNSPLASH_REMOTE_KEYS_TABLE = "remote_keys_table"
    const val PIX_PERF_DATABASE = "unsplash_images.db"

    const val ITEMS_PER_PAGE = 10
}