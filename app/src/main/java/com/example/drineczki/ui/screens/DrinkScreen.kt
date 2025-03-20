package com.example.drineczki.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.data.model.Skladnik

@Composable
fun DrinkScreen(navController: NavController, id: Int, database: MyDatabase){

    val viewModel = remember { DrinkViewModel(database) }

    val koktajl by viewModel.koktajl.collectAsState()
    val skladniki by viewModel.skladniki.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load(id)
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (koktajl == null){
        Text("Error")
    } else {
        Column (


        ){

            Text(koktajl!!.nazwa?:"")
            Spacer(modifier = Modifier.height(20.dp))
            Text(koktajl!!.przepis?:"")

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

}

@Composable
fun SkladnikItem(skladnik: Skladnik) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
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