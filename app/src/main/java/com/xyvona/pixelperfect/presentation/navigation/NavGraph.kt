package com.xyvona.pixelperfect.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.xyvona.pixelperfect.presentation.favorites_screen.FavoriteViewModel
import com.xyvona.pixelperfect.presentation.favorites_screen.FavoritesScreen
import com.xyvona.pixelperfect.presentation.full_image_screen.FullImageScreen
import com.xyvona.pixelperfect.presentation.full_image_screen.FullImageViewModel
import com.xyvona.pixelperfect.presentation.home_screen.HomeScreen
import com.xyvona.pixelperfect.presentation.home_screen.HomeViewModel
import com.xyvona.pixelperfect.presentation.profile_screen.ProfileScreen
import com.xyvona.pixelperfect.presentation.search_screen.SearchScreen
import com.xyvona.pixelperfect.presentation.search_screen.SearchViewModel
import com.xyvona.pixelperfect.presentation.util.SnackbarEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val images = homeViewModel.images.collectAsLazyPagingItems()
            val favoriteImageIds by homeViewModel.favoriteImageIds.collectAsStateWithLifecycle()
            HomeScreen(
                snackbarHostState = snackbarHostState,
                snackbarEvent = homeViewModel.snackbarEvent,
                scrollBehavior = scrollBehavior,
                images = images,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = { navController.navigate(Routes.FavoritesScreen) },
                favoriteImageIds = favoriteImageIds,
                onToggleFavoriteStatus = { homeViewModel.toggleFavoriteStatus(it) }
            )
        }
        composable<Routes.SearchScreen> {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val searchedImages = searchViewModel.searchImages.collectAsLazyPagingItems()
            val favoriteImageIds by searchViewModel.favoriteImagesId.collectAsStateWithLifecycle()
            SearchScreen(
                snackbarHostState = snackbarHostState,
                snackbarEvent = searchViewModel.snackbarEvent,
                searchedImages = searchedImages,
                favoriteImageIds = favoriteImageIds,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearch = { searchViewModel.searchImages(it) },
                onToggleFavoriteStatus = { searchViewModel.toggleFavoriteStatus(it) }
            )
        }
        composable<Routes.FavoritesScreen> {
            val favoritesViewModel: FavoriteViewModel = hiltViewModel()
            val favoriteImages = favoritesViewModel.favoriteImages.collectAsLazyPagingItems()
            val favoriteImageIds by favoritesViewModel.favoriteImagesId.collectAsStateWithLifecycle()

            FavoritesScreen(
                snackbarHostState = snackbarHostState,
                favoriteImages = favoriteImages,
                snackbarEvent = favoritesViewModel.snackbarEvent,
                favoriteImageIds = favoriteImageIds,
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onToggleFavoriteStatus = { favoritesViewModel.toggleFavoriteStatus(it) } ,
                scrollBehavior = scrollBehavior,
                onSearchClick = { navController.navigate(Routes.SearchScreen) }
            )
        }
        composable<Routes.FullImageScreen> {
            val fullImageViewModel: FullImageViewModel = hiltViewModel()
            FullImageScreen(
                snackbarHostState = snackbarHostState,
                snackbarEvent = fullImageViewModel.snackbarEvent,
                image = fullImageViewModel.image,
                onBackClick = { navController.navigateUp() },
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
                profileLink = profileLink,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}