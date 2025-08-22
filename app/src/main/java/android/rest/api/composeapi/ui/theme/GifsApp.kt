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
import android.rest.api.composeapi.model.GifItem
import android.rest.api.composeapi.model.Images
import android.rest.api.composeapi.model.OriginalImage
import android.rest.api.composeapi.ui.theme.screens.DetailsScreen
import android.rest.api.composeapi.ui.theme.screens.GifsUiState
import android.rest.api.composeapi.ui.theme.screens.GifsViewModel
import android.rest.api.composeapi.ui.theme.screens.HomeScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GifsApp() {
    val gifsViewModel: GifsViewModel = viewModel(factory = GifsViewModel.Factory)
    val navController: NavHostController = rememberNavController()
    // State для поиска
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
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
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { query ->
                                searchQuery = query
                                // Запускаем поиск в зависимости от активной вкладки
                                when (pagerState.currentPage) {
                                    0 -> gifsViewModel.searchGifs(query)
                                    1 -> gifsViewModel.searchStickers(query)
                                }
                            },
                            onSearch = { query ->
                                isSearchActive = false
                                // Поиск уже запущен в onQueryChange
                            },
                            active = isSearchActive,
                            onActiveChange = { isSearchActive = it },
                            placeholder = {
                                Text(
                                    text = if (pagerState.currentPage == 0) "Поиск GIF..." else "Поиск стикеров..."
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Поиск"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            // Здесь можно добавить историю поиска или предложения
                            // Пока оставим пустым
                        }
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
                                    gifsUiState = gifsViewModel.getCurrentGifsState(),
                                    retryAction = gifsViewModel::getGifs,
                                    onGifClick = { gifUrl ->
                                        println("Navigating to details with URL: $gifUrl")
                                        val encodedUrl = Uri.encode(gifUrl)
                                        navController.navigate("details/$encodedUrl")
                                    },
                                    contentPadding = PaddingValues(0.dp)
                                )
                                1 -> HomeScreen(
                                    gifsUiState = gifsViewModel.getCurrentStickersState(),
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


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Create a mock GifsUiState with sample data for the preview
    val mockUiState = GifsUiState.Success(
        gifs = listOf(
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif1.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif2.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif3.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif4.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif5.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif6.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif7.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif8.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif9.gif"
                    )
                )
            ),
            GifItem(
                images = Images(
                    original = OriginalImage(
                        url = "https://example.com/gif10.gif"
                    )
                )
            ),

            ),

        )

    // Call the HomeScreen with mock data
    HomeScreen(
        gifsUiState = mockUiState,
        retryAction = {}, // Provide an empty lambda since no action is needed
        onGifClick = {},
        contentPadding = PaddingValues(16.dp)
    )
}
