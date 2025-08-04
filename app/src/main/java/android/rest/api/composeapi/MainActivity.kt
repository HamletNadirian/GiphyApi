package android.rest.api.composeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.rest.api.composeapi.ui.theme.ComposeApiTheme
import android.rest.api.composeapi.ui.theme.GifsPhotosApp
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeApiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()){
                    GifsPhotosApp()
                }

            }
        }
    }
}
