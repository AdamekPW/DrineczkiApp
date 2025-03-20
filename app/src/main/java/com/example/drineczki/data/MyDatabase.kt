package com.example.drineczki.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.drineczki.data.model.Koktajl
import com.example.drineczki.data.model.KoktajlDao
import com.example.drineczki.data.model.Skladnik
import com.example.drineczki.data.model.SkladnikDao

@Database(entities = [Koktajl::class, Skladnik::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun koktajlDao(): KoktajlDao
    abstract fun skladnikDao(): SkladnikDao
}