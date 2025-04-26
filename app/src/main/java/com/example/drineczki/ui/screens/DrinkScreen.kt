package com.example.drineczki.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drineczki.R
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Skladnik
import com.example.drineczki.ui.components.TimerScreen
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkScreen(navController: NavController?, id: Int, database: MyDatabase) {
    val viewModel = remember { DrinkViewModel(database) }
    val koktajl by viewModel.koktajl.collectAsState()
    val skladniki by viewModel.skladniki.collectAsState()
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val iconResId = when (id % 2 + 1) {
        1 -> R.drawable.drink_1
        2 -> R.drawable.drink_2
        else -> R.drawable.drink_1
    }

    LaunchedEffect(Unit) {
        viewModel.load(id)
    }

    Scaffold(
        topBar = {
            Box {
                // Obraz jako tło
                val collapsedFraction = scrollBehavior.state.collapsedFraction
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Tło drinka",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((250 * (1f - collapsedFraction)).dp) // Dynamiczna wysokość obrazu
                        .graphicsLayer { alpha = 1f - collapsedFraction } // Dynamiczna przezroczystość obrazu
                )

                // Pasek aplikacji nałożony na obraz
                LargeTopAppBar(
                    title = {
                        Text(
                            text = koktajl?.nazwa ?: "Szczegóły drinka",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        if (navController != null) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Wstecz", tint = Color.White)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Transparent, // Przezroczyste tło paska
                        scrolledContainerColor = Color.Black
                    ),
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.background(Color.Transparent) // Przezroczystość
                )
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
        containerColor = Color(0xFFa66730),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }

            if (koktajl == null) {
                item {
                    Text("Nie znaleziono drinka", color = Color.Red, fontSize = 20.sp)
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
                            TimerScreen(key = id)
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
