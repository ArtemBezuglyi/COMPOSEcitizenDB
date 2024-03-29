package ru.artbez.composecitizendb.data.base


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CitizenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCitizen(citizenEntity: CitizenEntity)

    @Update
    suspend fun updateCitizen(citizenEntity: CitizenEntity)

    @Delete
    suspend fun deleteCitizen(citizenEntity: CitizenEntity)

    @Query("DELETE FROM citizen")
    suspend fun clearAllCitizen()

    @Query("SELECT * FROM citizen WHERE ((name_F LIKE '%' || :search || '%') || (name_I LIKE '%' || :search || '%') || (name_O LIKE '%' || :search || '%') || (birthday_date LIKE '%' || :search || '%'))")
    suspend fun searchCitizen(search: String): List<CitizenEntity>

    @Query("SELECT * FROM citizen")
    fun getAll(): Flow<List<CitizenEntity>>

}