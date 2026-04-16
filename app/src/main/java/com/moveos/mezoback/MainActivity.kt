package com.moveos.mezoback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moveos.mezoback.ui.MEZOBackApp
import com.moveos.mezoback.ui.MainViewModel
import com.moveos.mezoback.ui.theme.MEZOBackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MEZOBackTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val vm: MainViewModel = viewModel()
                    MEZOBackApp(vm)
                }
            }
        }
    }
}
