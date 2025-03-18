package com.example.drineczki.data.model

import android.content.Context
import androidx.room.*

// Model dla tabeli "Koktajle"
@Entity(tableName = "Koktajle")
data class Koktajl(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nazwa: String,
    val przepis: String
)

// Model dla tabeli "Skladniki"
@Entity(
    tableName = "Skladniki",
    foreignKeys = [ForeignKey(
        entity = Koktajl::class,
        parentColumns = ["id"],
        childColumns = ["id_koktajlu"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Skladnik(
    @PrimaryKey(autoGenerate = true) val id_skladnika: Int = 0,
    val id_koktajlu: Int,
    val nazwa_skladnika: String
)

// DAO dla Koktajle
@Dao
interface KoktajlDao {
    @Insert
    suspend fun insertKoktajl(koktajl: Koktajl): Long

    @Query("SELECT * FROM Koktajle")
    suspend fun getAllKoktajle(): List<Koktajl>
}

// DAO dla Skladniki
@Dao
interface SkladnikDao {
    @Insert
    suspend fun insertSkladnik(skladnik: Skladnik)

    @Query("SELECT * FROM Skladniki WHERE id_koktajlu = :koktajlId")
    suspend fun getSkladnikiForKoktajl(koktajlId: Int): List<Skladnik>
}

// Baza danych Room
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
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}