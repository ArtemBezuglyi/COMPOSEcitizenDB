package ru.artbez.composecitizendb.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CitizenEntity::class
    ],
    version = 1
)
abstract class CitizenDatabase : RoomDatabase() {
    abstract val citizenDao : CitizenDao
    companion object{
        fun createDatabase(context: Context): CitizenDatabase {
            return Room.databaseBuilder(
                context,
                CitizenDatabase::class.java,
                "test.db"
            ).build()
        }
    }
}