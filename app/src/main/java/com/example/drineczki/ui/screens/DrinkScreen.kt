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
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.data.model.Skladnik
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send


@Composable
fun DrinkScreen(navController: NavController?, id: Int, database: MyDatabase) {
    val viewModel = remember { DrinkViewModel(database) }

    val koktajl by viewModel.koktajl.collectAsState()
    val skladniki by viewModel.skladniki.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.load(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFa66730))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (navController != null) {
                Button(
                    onClick = { navController.navigate("DrinkListScreen") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Powrót do listy")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TimerScreen(key = id)

            Spacer(modifier = Modifier.height(20.dp))

            if (koktajl == null) {
                Text("", color = Color.Red, fontSize = 20.sp)
            } else {
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
                        Text(koktajl!!.nazwa ?: "Brak nazwy", style = MaterialTheme.typography.headlineLarge)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(koktajl!!.przepis ?: "Brak przepisu", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (skladniki.isEmpty()) {
                    Text(text = "Brak składników", fontSize = 18.sp, color = Color.Gray)
                } else {
                    LazyColumn {
                        items(skladniki) { skladnik ->
                            SkladnikItem(skladnik)
                        }
                    }
                }
            }
        }

        // 🌟 FAB do wysyłania SMS
        FloatingActionButton(
            onClick = {
                val message = buildString {
                    append("Składniki drinka: ${koktajl?.nazwa ?: "Nieznany"}\n")
                    skladniki.forEach {
                        append("- ${it.nazwaSkladnika ?: "Nieznany składnik"}\n")
                    }
                }
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("sms:")
                    putExtra("sms_body", message)
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFFfab170),
            contentColor = Color.Black
        ) {
            Icon(Icons.Default.Send, contentDescription = "Wyślij SMS")
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
