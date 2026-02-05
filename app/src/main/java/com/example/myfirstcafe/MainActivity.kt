package com.example.myfirstcafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.myfirstcafe.core.navigation.AppNavGraph
import com.example.myfirstcafe.core.ui.theme.CafeTheme
import com.example.myfirstcafe.data.repository.MenuRepository
import com.example.myfirstcafe.data.seed.SeedData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: MenuRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyFirstCafe)
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            repository.seedIfEmpty(SeedData.categories, SeedData.items)
        }
        enableEdgeToEdge()
        setContent {
            CafeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}