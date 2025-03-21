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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = (application as MyApplication).database

        enableEdgeToEdge()
        setContent {
            DrineczkiTheme {
                AppNavigation(database)
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
