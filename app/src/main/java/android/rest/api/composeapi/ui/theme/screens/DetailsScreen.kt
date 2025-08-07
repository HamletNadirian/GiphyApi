package android.rest.api.composeapi.ui.theme.screens

import android.rest.api.composeapi.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.ImageView
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.Glide

@Composable
fun DetailsScreen(
    gifUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onBackClick() },
        contentAlignment = Alignment.Center
    ) {

        AndroidView(
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
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
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

