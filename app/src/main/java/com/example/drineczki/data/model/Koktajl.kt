package com.example.drineczki.data.model

import androidx.room.*

@Entity(tableName = "Koktajle")
data class Koktajl(
    @PrimaryKey(autoGenerate = true) val id: Int? = 1,
    @ColumnInfo(name = "nazwa") val nazwa: String?,
    @ColumnInfo(name = "przepis") val przepis: String?
)

@Dao
interface KoktajlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKoktajl(koktajl: Koktajl): Long

    @Query("SELECT * FROM Koktajle WHERE id = :id")
    suspend fun getKoktajlById(id: Int): Koktajl?

    @Query("SELECT * FROM Koktajle")
    suspend fun getAllKoktajle(): List<Koktajl>
}
