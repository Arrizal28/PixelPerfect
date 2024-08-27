package com.xyvona.pixelperfect.domain.repository

import com.xyvona.pixelperfect.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityObserver {
    val networkStatus: StateFlow<NetworkStatus>
}