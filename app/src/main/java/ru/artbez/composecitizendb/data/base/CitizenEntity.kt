package ru.artbez.composecitizendb.data.base

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "citizen")
data class CitizenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "name_F")
    val name1: String,
    @ColumnInfo(name = "name_I")
    val name2: String,
    @ColumnInfo(name = "name_O")
    val name3: String,
    @ColumnInfo(name = "birthday_date")
    val birthday: String,
    @ColumnInfo(name = "nation_id")
    val nation: Int
)
