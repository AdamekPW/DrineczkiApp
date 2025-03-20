package com.example.drineczki

import android.app.Application
import com.example.drineczki.data.DatabaseProvider
import com.example.drineczki.data.MyDatabase

class MyApplication : Application() {
    val database: MyDatabase by lazy { DatabaseProvider.getDatabase(this) }
}
