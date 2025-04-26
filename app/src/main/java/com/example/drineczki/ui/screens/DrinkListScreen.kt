package com.example.drineczki.ui.screens

import androidx.collection.emptyLongSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.ui.components.RandomDrinkIcon

@Composable
fun DrinkListScreen(navController: NavController? = null, database: MyDatabase, onDrinkSelected: ((Int) -> Unit)? = null) {

    val viewModel = remember { DrinkListViewModel(database) }

    val koktajle by viewModel.koktajle.collectAsState()

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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp), // minimum szerokość elementu
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(koktajle) { koktajl ->
                KoktajlItem(navController, koktajl = koktajl, onDrinkSelected)
            }
        }
    }
}

@Composable
fun KoktajlItem(navController: NavController?, koktajl: Koktajl, onDrinkSelected: ((Int) -> Unit)? = null) {
    Card(
        modifier = if (navController == null)
        {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        }
        else
        {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(160.dp)
        },
        elevation = CardDefaults.cardElevation(4.dp),

        onClick = {
            val id = koktajl.id
            onDrinkSelected?.invoke(id!!)
            if( navController == null)
            {

            }
            else
            {
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
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = koktajl.nazwa?:"",
                style = MaterialTheme.typography.headlineMedium
            )
            RandomDrinkIcon()
        }
    }
}
