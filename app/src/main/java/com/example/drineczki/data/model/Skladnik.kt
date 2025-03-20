package com.example.drineczki.data.model

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(
    tableName = "Skladniki",
    foreignKeys = [
        ForeignKey(
            entity = Koktajl::class,
            parentColumns = ["id"],
            childColumns = ["id_koktajlu"]
        )
    ],
    indices = [Index(value = ["id_koktajlu"])]
)
data class Skladnik(
    @PrimaryKey(autoGenerate = true) val id_skladnika: Int? = 0,
    @ColumnInfo(name = "id_koktajlu") val idKoktajlu: Int?,
    @ColumnInfo(name = "nazwa_skladnika") val nazwaSkladnika: String?
)

@Dao
interface SkladnikDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkladnik(skladnik: Skladnik)

    @Query("SELECT * FROM Skladniki WHERE id_koktajlu = :koktajlId")
    suspend fun getSkladnikiByKoktajlId(koktajlId: Int): List<Skladnik>
}