package com.example.drineczki.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drineczki.data.model.SharedStuffViewModel

@Composable
fun TimerScreen(sharedStuffViewModel: SharedStuffViewModel) {
    val viewModel: TimerViewModel = viewModel(
        key = (sharedStuffViewModel.id_drinka?:0).toString(),
        modelClass = TimerViewModel::class.java
    )

    val timeLeft by viewModel.timeLeft.collectAsState()
    sharedStuffViewModel.time_left = timeLeft

    LaunchedEffect(sharedStuffViewModel.id_drinka) {
        viewModel.setTimer(sharedStuffViewModel.time_left)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pozostały czas: ${formatTime(timeLeft)}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { viewModel.startTimer() }) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Heart", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.stopTimer() }) {
                Icon(Icons.Default.Clear, contentDescription = "Heart", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.resetTimer() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Heart", tint = Color.Red)
            }
        }
    }
}



private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val sec = seconds % 60
    return String.format("%02d:%02d", minutes, sec)
}

