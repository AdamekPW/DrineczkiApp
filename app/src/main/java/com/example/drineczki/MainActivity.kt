package com.example.drineczki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.ui.screens.DrinkListScreen
import com.example.drineczki.ui.screens.DrinkScreen
import com.example.drineczki.ui.screens.InfoScreen
import com.example.drineczki.ui.theme.DrineczkiTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.media3.common.util.Log
import androidx.compose.material3.*
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drineczki.data.model.SharedStuffViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = (application as MyApplication).database


        enableEdgeToEdge()
        setContent {
            val sharedStuffViewModel: SharedStuffViewModel = viewModel()
            val windowSizeClass = calculateWindowSizeClass(this)
            DrineczkiTheme {
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                    TabletLayout(database, sharedStuffViewModel)
                } else {
                    AppNavigation(database, sharedStuffViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(database: MyDatabase, sharedStuffViewModel: SharedStuffViewModel ) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.background(Color(0xFFa66730))
            ) {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
                Divider(color = Color.Black, thickness = 1.dp)

                // Przycisk do DrinkListScreen
                NavigationDrawerItem(
                    label = { Text("Lista drinków", color = Color.Black) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("DrinkListScreen")
                    },
                    modifier = Modifier.padding(8.dp)
                )

                // Przycisk do InfoScreen
                NavigationDrawerItem(
                    label = { Text("Info", color = Color.Black) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("Info")
                    },
                    modifier = Modifier.padding(8.dp)
                )

                // Martwe przyciski
                NavigationDrawerItem(
                    label = { Text("Opcja 1", color = Color.Black) },
                    selected = false,
                    onClick = { /* Martwy przycisk */ },
                    modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Opcja 2", color = Color.Black) },
                    selected = false,
                    onClick = { /* Martwy przycisk */ },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "DrinkListScreen") {
            composable("DrinkListScreen") { 
                DrinkListScreen(
                    navController = navController,
                    database = database,
                    sharedStuffViewModel
                )
            }
            composable("Info") { InfoScreen(navController) }
            composable(
                "Drink/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                DrinkScreen(navController, sharedStuffViewModel, database)
            }
        }
    }
}

@Composable
fun TabletLayout(database: MyDatabase, sharedStuffViewModel: SharedStuffViewModel ) {
    //var selectedDrinkId by remember { mutableStateOf<Int?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        DrinkListScreen(
            navController = null,
            database = database,
            sharedStuffViewModel,
            onDrinkSelected = { id ->
                sharedStuffViewModel.id_drinka = id
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFFFDE8D7))
        ) {
            sharedStuffViewModel.id_drinka?.let { id ->
                key(id)
                {
                    DrinkScreen(navController = null, sharedStuffViewModel, database = database)
                }

            } ?: Text(
                "Wybierz drinka z listy",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

