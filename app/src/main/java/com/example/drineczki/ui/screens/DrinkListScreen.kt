package com.example.drineczki.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.ui.components.RandomDrinkIcon

@Composable
fun DrinkListScreen(
    navController: NavController? = null,
    database: MyDatabase,
    onDrinkSelected: ((Int) -> Unit)? = null
) {
    val viewModel = remember { DrinkListViewModel(database) }
    val easyDrinks by viewModel.easyDrinks.collectAsState()
    val hardDrinks by viewModel.hardDrinks.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadKoktajle()
    }

    Column(
        modifier = if (navController == null) {
            Modifier
                .fillMaxWidth(0.33f)
                .background(Color(0xFFa66730))
        } else {
            Modifier
                .fillMaxSize()
                .background(Color(0xFFa66730))
        }
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Zakładki (TabRow)
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFFfab170),
            contentColor = Color.Black
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Łatwe") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Trudne") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Siatka koktajli
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            val drinksToShow = if (selectedTab == 0) easyDrinks else hardDrinks
            items(drinksToShow) { koktajl ->
                KoktajlItem(navController, koktajl = koktajl, onDrinkSelected)
            }
        }
    }
}

@Composable
fun KoktajlItem(
    navController: NavController?,
    koktajl: Koktajl,
    onDrinkSelected: ((Int) -> Unit)? = null
) {
    Card(
        modifier = if (navController == null) {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(160.dp)
        },
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            val id = koktajl.id
            onDrinkSelected?.invoke(id!!)
            if (navController != null) {
                navController.navigate("Drink/$id")
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFfab170),
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = koktajl.nazwa ?: "",
                style = MaterialTheme.typography.headlineMedium
            )
            RandomDrinkIcon()
        }
    }
}
