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

    private val _easyDrinks = MutableStateFlow<List<Koktajl>>(emptyList())
    val easyDrinks = _easyDrinks.asStateFlow()

    private val _hardDrinks = MutableStateFlow<List<Koktajl>>(emptyList())
    val hardDrinks = _hardDrinks.asStateFlow()

    fun loadKoktajle() {
        viewModelScope.launch {
            val allKoktajle = database.koktajlDao().getAllKoktajle()

            val easy = mutableListOf<Koktajl>()
            val hard = mutableListOf<Koktajl>()

            for (koktajl in allKoktajle) {
                val skladniki = database.skladnikDao().getSkladnikiByKoktajlId(koktajl.id!!)
                if (skladniki.size < 4) {
                    easy.add(koktajl)
                } else {
                    hard.add(koktajl)
                }
            }

            _easyDrinks.value = easy
            _hardDrinks.value = hard
        }
    }
}
