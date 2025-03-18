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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "koktajle_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                //executeSqlFromAssets(context, db, "dane.sql")
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

//        private fun executeSqlFromAssets(context: Context, db: SupportSQLiteDatabase, fileName: String) {
//            try {
//                context.assets.open(fileName).use { inputStream ->
//                    BufferedReader(InputStreamReader(inputStream)).use { reader ->
//                        var line: String?
//                        while (reader.readLine().also { line = it } != null) {
//                            if (!line.isNullOrBlank() && !line!!.startsWith("--")) { // Pomijanie komentarzy
//                                db.execSQL(line)
//                            }
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }
}
