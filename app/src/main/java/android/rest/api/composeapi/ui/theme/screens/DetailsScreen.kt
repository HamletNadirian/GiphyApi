package android.rest.api.composeapi.ui.theme.screens

import android.rest.api.composeapi.R

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.ImageView
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.Glide

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade


@Composable
fun DetailsScreen(
    gifUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (gifUrl.isEmpty()) {
            Text("Ошибка: URL изображения пустой")
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gifUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "GIF изображение",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image)
            )
            /*           AndroidView(
                factory = {
                    ImageView(it).apply {
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        Glide.with(context)
                            .asGif()
                            .load(gifUrl)
                            .placeholder(R.drawable.loading_img)
                            .error(R.drawable.ic_broken_image)
                            .into(this)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )*/
        }
        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Назад")
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        gifUrl = "https://media.giphy.com/media/3o7TKsWn5gAdS45x0Q/giphy.gif",
        onBackClick = {}
    )
}

