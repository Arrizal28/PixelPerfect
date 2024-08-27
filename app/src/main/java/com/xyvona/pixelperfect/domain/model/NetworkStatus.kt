package com.xyvona.pixelperfect.domain.model

sealed class NetworkStatus {

    data object Connected : NetworkStatus()

    data object Disconnected : NetworkStatus()
}