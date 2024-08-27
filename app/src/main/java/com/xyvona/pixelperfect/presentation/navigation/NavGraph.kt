package com.xyvona.pixelperfect.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.xyvona.pixelperfect.presentation.favorites_screen.FavoritesScreen
import com.xyvona.pixelperfect.presentation.full_image_screen.FullImageScreen
import com.xyvona.pixelperfect.presentation.full_image_screen.FullImageViewModel
import com.xyvona.pixelperfect.presentation.home_screen.HomeScreen
import com.xyvona.pixelperfect.presentation.home_screen.HomeViewModel
import com.xyvona.pixelperfect.presentation.profile_screen.ProfileScreen
import com.xyvona.pixelperfect.presentation.search_screen.SearchScreen
import com.xyvona.pixelperfect.presentation.util.SnackbarEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                snackbarHostState = snackbarHostState,
                snackbarEvent = homeViewModel.snackbarEvent,
                scrollBehavior = scrollBehavior,
                images = homeViewModel.images,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                               },
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                },
                onFABClick = { navController.navigate(Routes.FavoritesScreen) })
        }
        composable<Routes.SearchScreen> {
            SearchScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FavoritesScreen> {
            FavoritesScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FullImageScreen> {
            val fullImageViewModel: FullImageViewModel = hiltViewModel()
            FullImageScreen(
                snackbarHostState = snackbarHostState,
                snackbarEvent = fullImageViewModel.snackbarEvent,
                onBackClick = { navController.navigateUp() },
                image = fullImageViewModel.image,
                onPhotographerNameClick = { profileLink ->
                    navController.navigate(Routes.ProfileScreen(profileLink))
                },
                onImageDownloadClick = { url, title ->
                    fullImageViewModel.downloadImage(url, title)
                }
            )
        }
        composable<Routes.ProfileScreen> { backStackEntry ->
            val profileLink = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink
            ProfileScreen(
                onBackClick = { navController.navigateUp() },
                profileLink = profileLink
            )
        }
    }
}