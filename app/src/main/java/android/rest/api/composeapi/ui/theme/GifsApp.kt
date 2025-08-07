@file:OptIn(ExperimentalMaterial3Api::class)

package android.rest.api.composeapi.ui.theme

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.rest.api.composeapi.R
import android.rest.api.composeapi.ui.theme.screens.DetailsScreen
import android.rest.api.composeapi.ui.theme.screens.GifsViewModel
import android.rest.api.composeapi.ui.theme.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifsApp() {
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
            val gifsViewModel: GifsViewModel = viewModel(factory = GifsViewModel.Factory)

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        gifsUiState = gifsViewModel.gifsUiState,
                        retryAction = gifsViewModel::getGifs,
                        onGifClick = { gifUrl ->
                            // Кодируем URL для безопасной передачи
                            val encodedUrl = Uri.encode(gifUrl)
                            navController.navigate("details/$encodedUrl")
                        }
                    )
                }

                composable("details/{gifUrl}") { backStackEntry ->
                    val encodedGifUrl = backStackEntry.arguments?.getString("gifUrl") ?: ""
                    val gifUrl = Uri.decode(encodedGifUrl)

                    DetailsScreen(
                        gifUrl = gifUrl,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}