package com.example.drineczki.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.data.model.KoktajlDao
import com.example.drineczki.data.model.Skladnik
import com.example.drineczki.data.model.SkladnikDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@Database(entities = [Koktajl::class, Skladnik::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun koktajlDao(): KoktajlDao
    abstract fun skladnikDao(): SkladnikDao
}
