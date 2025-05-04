package com.example.drineczki.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "To jest aplikacja DrineczkiApp.\nZnajdź swoje ulubione drinki!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}