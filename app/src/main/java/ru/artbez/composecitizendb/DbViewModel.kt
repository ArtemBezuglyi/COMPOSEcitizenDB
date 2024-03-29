package ru.artbez.composecitizendb

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import ru.artbez.composecitizendb.data.base.CitizenDatabase
import ru.artbez.composecitizendb.data.base.CitizenEntity

class DbViewModel(val database: CitizenDatabase) : ViewModel() {

    val citizenList = database.citizenDao.getAll()
    val newName1 = mutableStateOf("")
    val newName2 = mutableStateOf("")
    val newName3 = mutableStateOf("")
    val newBirthday = mutableStateOf("")
    val newNation = mutableStateOf(1)
    var citizenEntity: CitizenEntity? = null
    val search = mutableStateOf("")
    val citizen5 = listOf(
        CitizenEntity(name1 = "Иванов", name2 = "Иван", name3 = "Иванович", birthday = "06.01.1966", nation = 1),
        CitizenEntity(name1 = "Петров", name2 = "Петр", name3 = "Петрович", birthday = "01.03.1970", nation = 1),
        CitizenEntity(name1 = "Сидоров", name2 = "Иван", name3 = "Петрович", birthday = "30.06.1981", nation = 15),
        CitizenEntity(name1 = "Козлов", name2 = "Павел", name3 = "Владимирович", birthday = "24.09.1993", nation = 15),
        CitizenEntity(name1 = "Смирнов", name2 = "Александр", name3 = "Сергеевич", birthday = "12.12.2000", nation = 1)
    )
    var searchList : List<CitizenEntity> = listOf(CitizenEntity(name1 = "", name2 = "", name3 = "", birthday = "", nation = 1))

    fun insertCitizen() = viewModelScope.launch {
        val citizen = citizenEntity?.copy(
            name1 = newName1.value,
            name2 = newName2.value,
            name3 = newName3.value,
            birthday = newBirthday.value,
            nation = newNation.value
        ) ?: CitizenEntity(
            name1 = newName1.value,
            name2 = newName2.value,
            name3 = newName3.value,
            birthday = newBirthday.value,
            nation = newNation.value
        )
        database.citizenDao.insertCitizen(citizen)
        citizenEntity = null
        newName1.value = ""
        newName2.value = ""
        newName3.value = ""
        newBirthday.value = ""
        newNation.value = 1
    }

    fun deleteCitizen(citizen: CitizenEntity) = viewModelScope.launch {
        database.citizenDao.deleteCitizen(citizen)
    }

    fun deleteAll() = viewModelScope.launch { database.citizenDao.clearAllCitizen() }

    fun insert5Citizen() = viewModelScope.launch {
       citizen5.forEach { database.citizenDao.insertCitizen(it) }
    }

    fun searchCitizen(searchWord: String) = viewModelScope.launch {
        searchList = database.citizenDao.searchCitizen(searchWord)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return DbViewModel(database = database) as T
            }
        }
    }

}