package android.rest.api.composeapi.ui.theme.screens

import android.content.ClipData
import android.content.Context
import android.content.Intent
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
import android.widget.Toast
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.Glide

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
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
        val clipboardManager = LocalClipboardManager.current

        Button(
            onClick = {
                val clipData = ClipData.newPlainText("plain text", "$gifUrl")
                val clipEntry = ClipEntry(clipData)
                clipboardManager.setClip(clipEntry)
            }
        ) {
            Text("Click to copy a text")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Share(text = gifUrl, context = LocalContext.current)
        }
    }
}
@Composable
fun Share(text: String, context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)


    Button(onClick = {
        startActivity(context, shareIntent, null)
    }) {
        Icon(imageVector = Icons.Default.Share, contentDescription = null)
        Text("Share", modifier = Modifier.padding(start = 8.dp))
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

