package com.example.drineczki.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrinkListViewModel(
    private val database: MyDatabase
) : ViewModel() {
    private val _koktajle = MutableStateFlow<List<Koktajl>>(emptyList())
    val koktajle = _koktajle.asStateFlow()

    fun loadKoktajle() {
        viewModelScope.launch {
            val result = database.koktajlDao().getAllKoktajle()
            _koktajle.value = result
        }
    }
}
