package com.example.drineczki.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl

@Composable
fun DrinkListScreen(database: MyDatabase) {

    val viewModel = remember { DrinkListViewModel(database) }

    val koktajle by viewModel.koktajle.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadKoktajle()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFa66730))
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn {
            items(koktajle) { koktajl ->
                KoktajlItem(koktajl = koktajl)
            }
        }
    }
}

@Composable
fun KoktajlItem(koktajl: Koktajl) {
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = koktajl.nazwa?:"",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
