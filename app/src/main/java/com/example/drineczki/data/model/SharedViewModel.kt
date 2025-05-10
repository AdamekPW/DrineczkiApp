package com.example.drineczki.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedStuffViewModel : ViewModel() {
    var id_drinka by mutableStateOf<Int?>(null)
}