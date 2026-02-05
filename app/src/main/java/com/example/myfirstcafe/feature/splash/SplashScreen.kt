package com.example.myfirstcafe.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myfirstcafe.R
import com.example.myfirstcafe.core.navigation.Routes
import com.example.myfirstcafe.core.ui.theme.BlackColor
import com.example.myfirstcafe.core.ui.strings.AppStrings
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val rotation = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        rotation.animateTo(targetValue = 180f, animationSpec = tween(1000, easing = LinearEasing))
        rotation.animateTo(targetValue = 0f, tween(1000, easing = LinearEasing))
        scale.animateTo(
            targetValue = 30f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
        delay(500)

        navController.navigate(Routes.MAIN) {
            popUpTo(Routes.SPLASH) {inclusive = true}
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(BlackColor)
    ) {
        Image(
            painter = painterResource(R.drawable.cafeicon),
            contentDescription = AppStrings.SPLASH,
            modifier = Modifier.graphicsLayer {
                rotationY = rotation.value
                scaleX = scale.value
                scaleY = scale.value
            }
        )
    }
}