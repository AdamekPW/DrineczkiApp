package com.example.drineczki.ui.screens

import com.example.drineczki.ui.components.TimerScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Skladnik
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.drineczki.R


@Composable
fun DrinkScreen(navController: NavController?, id: Int, database: MyDatabase) {
    val viewModel = remember { DrinkViewModel(database) }

    val koktajl by viewModel.koktajl.collectAsState()
    val skladniki by viewModel.skladniki.collectAsState()
    val context = LocalContext.current

    val iconResId = when (id % 2 + 1) {
        1 -> R.drawable.drink_1
        2 -> R.drawable.drink_2
        else -> R.drawable.drink_1
    }

    val imageModifier: Modifier = Modifier
        .fillMaxWidth()

    // Zmienna do śledzenia stanu przewijania
    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.load(id)
    }

    Scaffold(
        topBar = {
            val imageHeight = 200.dp - (scrollState.firstVisibleItemScrollOffset.dp.coerceAtMost(170.dp)) // Dynamically adjust height

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFa66730))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = iconResId),
                            contentDescription = "Drink",
                            contentScale = ContentScale.Crop, // ważne: Crop żeby ładnie wypełnił
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight.coerceAtLeast(30.dp)) // Minimalna wysokość 30.dp
                        )
                        Text(
                            text = "Szczegóły drinku",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.5f))
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    TimerScreen(key = id)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val message = buildString {
                        append("Składniki drinka: ${koktajl?.nazwa ?: "Nieznany"}\n")
                        skladniki.forEach {
                            append("- ${it.nazwaSkladnika ?: "Nieznany składnik"}\n")
                        }
                    }
                    val uri = Uri.parse("smsto:")
                    val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                        putExtra("sms_body", message)
                    }
                    context.startActivity(intent)
                },
                containerColor = Color(0xFFfab170),
                contentColor = Color.Black,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Wyślij SMS")
            }
        },
        containerColor = Color(0xFFa66730) // Tło całego ekranu
    ) { innerPadding ->
        LazyColumn(
            state = scrollState, // Przekazanie stanu scrollowania
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }

            if (koktajl == null) {
                item {
                    Text("", color = Color.Red, fontSize = 20.sp)
                }
            } else {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFfab170),
                            contentColor = Color.Black
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                koktajl!!.nazwa ?: "Brak nazwy",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                koktajl!!.przepis ?: "Brak przepisu",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }

                if (skladniki.isEmpty()) {
                    item {
                        Text(text = "Brak składników", fontSize = 18.sp, color = Color.Gray)
                    }
                } else {
                    items(skladniki) { skladnik ->
                        SkladnikItem(skladnik)
                    }
                }
            }
        }
    }
}





@Composable
fun SkladnikItem(skladnik: Skladnik) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFfab170),
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = skladnik.nazwaSkladnika ?: "Nieznany składnik", fontSize = 18.sp)
        }
    }
}
