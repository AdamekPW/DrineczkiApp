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
import com.example.drineczki.ui.theme.DrineczkiTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.media3.common.util.Log

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = (application as MyApplication).database

        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            DrineczkiTheme {
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                    TabletLayout(database)
                } else {
                    AppNavigation(database)
                }
            }
        }
    }
}

@Composable
fun AppNavigation( database: MyDatabase ){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "DrinkListScreen"){
        composable("DrinkListScreen") { DrinkListScreen(navController, database) }
        composable(
            "Drink/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DrinkScreen(navController, id, database)
        }
    }

}


@Composable
fun TabletLayout(database: MyDatabase) {
    var selectedDrinkId by remember { mutableStateOf<Int?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        DrinkListScreen(
            navController = null,
            database = database,
            onDrinkSelected = { id ->
                selectedDrinkId = id
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFFFDE8D7))
        ) {
            selectedDrinkId?.let { id ->
                key(id)
                {
                    DrinkScreen(navController = null, id = id, database = database)
                }

            } ?: Text(
                "Wybierz drinka z listy",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

