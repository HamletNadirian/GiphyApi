package android.rest.api.composeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import android.rest.api.composeapi.ui.theme.ComposeApiTheme
import android.rest.api.composeapi.ui.theme.GifsApp
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeApiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                   GifsApp()
                }
            }
        }
    }
}