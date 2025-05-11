package com.example.drineczki.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.drineczki.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFa66730))
    ) {
        // Pasek aplikacji
        TopAppBar(
            title = { Text("Info", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFa66730)
            ),
            navigationIcon = {
                if (navController != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz", tint = Color.White)
                    }
                }
            }
        )

        // Treść ekranu
        FloatingCirclesDemo()
    }
}


@Composable
fun FloatingCirclesDemo() {
    val circles = remember { List(3) { Animatable(0f) } }

    LaunchedEffect(Unit) {
        circles.forEachIndexed { index, animatable ->
            launch {
                delay(index * 300L)
                while (true) {
                    animatable.snapTo(0f)
                    animatable.animateTo(
                        targetValue = -500f,
                        animationSpec = tween(durationMillis = 6000, easing = LinearEasing)
                    )
                    delay(500L)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        // 🖼️ Obrazek w tle
        Text(
            text = "To jest aplikacja DrineczkiApp.\nZnajdź swoje ulubione drinki!\n\nAutorzy:\nAdam Szułczyński 155938\nPiotr Nowacki 155906",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(16.dp).fillMaxWidth(),

        )
        Image(
            painter = painterResource(id = R.drawable.drink_icon_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.scale(0.6f, 0.6f)
                .align(Alignment.BottomCenter)
        )

        // 🔵 Animowane kółka – na wierzchu, na dole ekranu
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            circles.forEachIndexed { index, animatable ->
                Box(
                    modifier = Modifier
                        .offset(y = animatable.value.dp - 50.dp, x = (9 * (index % 2)).dp)
                        .size((20 + index * 2).dp)
                        .graphicsLayer {
                            alpha = (animatable.value / -300f)
                        }
                        .background(
                            color = Color.Cyan.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}