package android.rest.api.composeapi.ui.theme.screens

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.rest.api.composeapi.ui.theme.screens.DetailsScreen
import android.rest.api.composeapi.ui.theme.screens.GifsViewModel
import android.rest.api.composeapi.ui.theme.screens.HomeScreen

enum class GifsScreen(val route: String) {
    Home("home"),
    Details("details/{gifUrl}")
}

