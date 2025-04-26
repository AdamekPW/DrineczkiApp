package com.example.drineczki.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import com.example.drineczki.R
import kotlin.random.Random

@Composable
fun RandomDrinkIcon(modifier: Modifier = Modifier) {
    val randomNumber = Random.nextInt(1, 5)

    val iconResId = when (randomNumber) {
        1 -> R.drawable.drink_icon_1
        2 -> R.drawable.drink_icon_2
        3 -> R.drawable.drink_icon_3
        4 -> R.drawable.drink_icon_4
        else -> R.drawable.drink_icon_1
    }

    Image(
        painter = painterResource(id = iconResId),
        contentDescription = "Drink Icon",
        contentScale = ContentScale.Fit,
        modifier = modifier.size(64.dp)
    )
}
