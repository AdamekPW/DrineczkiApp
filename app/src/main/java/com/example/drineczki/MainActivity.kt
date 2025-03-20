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
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.ui.screens.DrinkListScreen
import com.example.drineczki.ui.theme.DrineczkiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = (application as MyApplication).database

        enableEdgeToEdge()
        setContent {
            DrineczkiTheme {
                AppContainer(database = database) {
                    DrinkListScreen(database)
                }
            }
        }
    }
}

@Composable
fun AppContainer(
    database: MyDatabase,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDatabase provides database
    ) {
        content()
    }
}

val LocalDatabase = staticCompositionLocalOf<MyDatabase> {
    error("Database not provided")
}

