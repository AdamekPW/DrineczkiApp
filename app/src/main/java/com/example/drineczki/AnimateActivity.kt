package com.example.drineczki

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.layout.offset
import kotlin.math.roundToInt
import kotlinx.coroutines.delay

class AnimateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateScreen {
                // Po animacji przejdź do MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun AnimateScreen(onAnimationEnd: () -> Unit) {
    val anim = remember { Animatable(-300f) } // Start off-screen to the left
    LaunchedEffect(Unit) {
        anim.animateTo(0f, animationSpec = tween(1200))
        delay(500)
        onAnimationEnd()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfab170)),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset(x = anim.value.dp, y = 0.dp)
        ) {
            for (i in 1..4) {
                Image(
                    painter = painterResource(id =
                        when(i) {
                            1 -> R.drawable.drink_icon_1
                            2 -> R.drawable.drink_icon_2
                            3 -> R.drawable.drink_icon_3
                            4 -> R.drawable.drink_icon_4
                            else -> R.drawable.drink_icon_1
                        }
                    ),
                    contentDescription = "Drink $i",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(80.dp)
                )
            }
        }
    }
}
