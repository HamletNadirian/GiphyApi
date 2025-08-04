@file:OptIn(ExperimentalMaterial3Api::class)

package android.rest.api.composeapi.ui.theme

import android.rest.api.composeapi.R
import android.rest.api.composeapi.ui.theme.screens.GifsViewModel
import android.rest.api.composeapi.ui.theme.screens.HomeScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifsPhotosApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { GifsTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
          val gifsViewModel: GifsViewModel =
              viewModel(factory = GifsViewModel.Factory)
            HomeScreen(
                gifsUiState = gifsViewModel.gifsUiState,
                contentPadding = it,
                retryAction = gifsViewModel::getGifs
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifsTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        //modifier = modifier
    )
}