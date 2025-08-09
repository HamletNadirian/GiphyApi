@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package android.rest.api.composeapi.ui.theme

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.rest.api.composeapi.R
import android.rest.api.composeapi.ui.theme.screens.DetailsScreen
import android.rest.api.composeapi.ui.theme.screens.GifsViewModel
import android.rest.api.composeapi.ui.theme.screens.HomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GifsApp() {
    val gifsViewModel: GifsViewModel = viewModel(factory = GifsViewModel.Factory)
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    val pagerState = rememberPagerState(pageCount = { 2 })
                    val coroutineScope = rememberCoroutineScope()

                    Column {
                        TabRow(selectedTabIndex = pagerState.currentPage) {
                            listOf("GIFs", "Stickers").forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                                )
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            when (page) {
                                0 -> HomeScreen(
                                    gifsUiState = gifsViewModel.gifsUiState,
                                    retryAction = gifsViewModel::getGifs,
                                    onGifClick = { gifUrl ->
                                        println("Navigating to details with URL: $gifUrl")
                                        val encodedUrl = Uri.encode(gifUrl)
                                        navController.navigate("details/$encodedUrl")
                                    },
                                    contentPadding = PaddingValues(0.dp)
                                )
                                1 -> HomeScreen(
                                    gifsUiState = gifsViewModel.stickerUiState,
                                    retryAction = gifsViewModel::getStickers,
                                    onGifClick = { gifUrl ->
                                        println("Navigating to details with URL: $gifUrl")
                                        val encodedUrl = Uri.encode(gifUrl)
                                        navController.navigate("details/$encodedUrl")
                                    },
                                    contentPadding = PaddingValues(0.dp)
                                )
                            }
                        }
                    }
                }
                composable("details/{gifUrl}") { backStackEntry ->
                    val encodedGifUrl = backStackEntry.arguments?.getString("gifUrl") ?: ""
                    val gifUrl = Uri.decode(encodedGifUrl)
                    println("DetailsScreen opened with URL: $gifUrl")
                    DetailsScreen(
                        gifUrl = gifUrl,
                        onBackClick = {
                            println("Navigating back from DetailsScreen")
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}