package com.xyvona.pixelperfect.data.util

import com.xyvona.pixelperfect.BuildConfig

object Constants {

    const val IV_LOG_TAG = "PixelPerfectLogs"


    const val API_KEY = BuildConfig.UNSPLASH_API_KEY
    const val BASE_URL = "https://api.unsplash.com/"

    const val FAVORITE_IMAGES_TABLE = "favorite_images_table"
    const val PIX_PERF_DATABASE = "unsplash_images.db"

    const val ITEMS_PER_PAGE = 10
}