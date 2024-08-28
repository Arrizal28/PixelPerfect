package com.xyvona.pixelperfect.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.xyvona.pixelperfect.domain.model.NetworkStatus
import com.xyvona.pixelperfect.domain.repository.NetworkConnectivityObserver
import com.xyvona.pixelperfect.presentation.component.NetworkStatusBar
import com.xyvona.pixelperfect.presentation.navigation.NavGraphSetup
import com.xyvona.pixelperfect.presentation.theme.PixelPerfectTheme
import com.xyvona.pixelperfect.presentation.theme.customGreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var connectivityObserver: NetworkConnectivityObserver

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val status by connectivityObserver.networkStatus.collectAsState()
            var showMessageBar by rememberSaveable {
                mutableStateOf(false)
            }
            var message by rememberSaveable {
                mutableStateOf("")
            }
            var backgroundColor by remember {
                mutableStateOf(Color.Red)
            }

            LaunchedEffect(key1 = status) {
                when(status) {
                    NetworkStatus.Connected -> {
                        message = "Connected to internet"
                        backgroundColor = customGreen
                        delay(2000)
                        showMessageBar = false
                    }
                    NetworkStatus.Disconnected -> {
                        message = "No internet connection"
                        backgroundColor = Color.Red
                        showMessageBar = true
                    }
                }
            }

            PixelPerfectTheme {
                val navController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                val snackbarHostState = remember { SnackbarHostState() }

                var searchQuery by rememberSaveable {
                    mutableStateOf("")
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    bottomBar = {
                        NetworkStatusBar(showMessageBar = showMessageBar, backgroundColor = backgroundColor, message = message)
                    }
                ) {
                    NavGraphSetup(
                        navController = navController,
                        scrollBehavior = scrollBehavior,
                        snackbarHostState = snackbarHostState,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it }
                    )
                }
            }
        }
    }
}