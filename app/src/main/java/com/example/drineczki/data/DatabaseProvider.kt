package com.example.drineczki.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DatabaseProvider {
    private var instance: MyDatabase? = null

    fun getDatabase(context: Context): MyDatabase {
        return instance ?: synchronized(this) {
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "koktajle.db"
            )
                .createFromAsset("koktajle.db") // Wczytuje bazę danych z folderu assets
                .build()

            instance = newInstance
            newInstance
        }
    }
}
