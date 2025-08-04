package android.rest.api.composeapi.ui.theme.screens

import android.rest.api.composeapi.R
import android.rest.api.composeapi.model.GifItem
import android.rest.api.composeapi.model.GifResponse
import android.rest.api.composeapi.model.Images
import android.rest.api.composeapi.model.OriginalImage
import android.rest.api.composeapi.ui.theme.ComposeApiTheme
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bumptech.glide.Glide
import com.bumptech.glide.gifdecoder.GifDecoder

@Composable
fun HomeScreen(
    gifsUiState: GifsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp))
{
    when (gifsUiState) {
        is GifsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GifsUiState.Success -> GifsGridScreen(gifsUiState.gifs, modifier,contentPadding)
        is GifsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}


/*@Composable
fun GifsCard(gif: GifItem, modifier: Modifier = Modifier){
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(gif.images.original.url)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.gifs_photo),
        modifier = Modifier.fillMaxWidth()
    )
    Log.d("GifsCard", "GIF URL: ${gif.images.original.url}")

}*/
@Composable
fun GifsCard(
    url:String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AndroidView(
            factory = {
                ImageView(it).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    Glide.with(context)
                        .asGif()
                        .load(url)
                        .placeholder(R.drawable.loading_img)          // заглушка при загрузке
                        .error(R.drawable.ic_broken_image)             // при ошибке
                        .into(this)

                }
            },
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun GifsGridScreen(
    gifs: List<GifItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = gifs, key = { gif -> gif.images.original.url }) { gif ->
            GifsCard(url = gif.images.original.url,
                modifier = Modifier
                    .padding(4.dp)
                .fillMaxWidth()
                .aspectRatio(1.5f)
            )
        }

    }
}
/*@Composable
fun GifsCard(gif: GifItem, modifier: Modifier = Modifier) {
    AsyncImage(
        model = gif.images.original.url,
        contentDescription = stringResource(R.string.gifs_photo),
        modifier = modifier.fillMaxWidth()
    )
}*/
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}
@Composable
fun ErrorScreen(retryAction:()-> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    ComposeApiTheme {
        LoadingScreen()
    }
}


@Preview
@Composable
fun GifsGridScreenPreview() {
    ComposeApiTheme {
        var mockData = List(10){ GifItem(images = Images(original = OriginalImage("url")))} }
        ResultScreen(stringResource(R.string.placeholder_success))
    }

