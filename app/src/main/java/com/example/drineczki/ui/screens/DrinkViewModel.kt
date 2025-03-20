package com.example.drineczki.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drineczki.data.MyDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.data.model.Skladnik
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrinkViewModel(
    private val database: MyDatabase
) : ViewModel() {
    private val _koktajl = MutableStateFlow<Koktajl?>(null)
    private val _skladniki = MutableStateFlow<List<Skladnik>>(emptyList())

    val koktajl = _koktajl.asStateFlow()
    val skladniki = _skladniki.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch {
            val k = database.koktajlDao().getKoktajlById(id)
            _koktajl.value = k

            val s = database.skladnikDao().getSkladnikiByKoktajlId(id)
            _skladniki.value = s
        }
    }
}

