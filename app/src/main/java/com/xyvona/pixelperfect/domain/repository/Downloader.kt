package com.xyvona.pixelperfect.domain.repository

interface Downloader {
    fun downloadIFile(url: String, fileName: String?)
}