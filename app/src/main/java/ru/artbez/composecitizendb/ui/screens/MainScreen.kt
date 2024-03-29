package ru.artbez.composecitizendb.ui.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.artbez.composecitizendb.DbViewModel
import ru.artbez.composecitizendb.data.NationList.nationalityList

@Composable
fun MainScreen(
    dbViewModel: DbViewModel = viewModel(factory = DbViewModel.factory),
    modifier: Modifier
) {

    val context = LocalContext.current

    var isAddNewScreenVisible by remember { mutableStateOf(false) }
    var isDataBaseVisible by remember { mutableStateOf(false) }
    var isSearchVisible by remember { mutableStateOf(false) }
    var nationality by rememberSaveable { mutableStateOf("РФ") }
    val citizenList = dbViewModel.citizenList.collectAsState(initial = emptyList())
    val searchList = dbViewModel.searchList
    var searchWord : String

    fun findNationId(nation: String) : Int {
        val list = nationalityList
        val foundElement = list.find {it.nationName == nation}
        return foundElement!!.id
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = dbViewModel.search.value,
                onValueChange = { dbViewModel.search.value = it},
                label = {
                    Text(text = "Search")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                )
            )
            IconButton(onClick = {
               searchWord = dbViewModel.search.value
               dbViewModel.searchCitizen(searchWord)
             //   searchList = dbViewModel.searchList
                isSearchVisible = true
                isDataBaseVisible = false
                isAddNewScreenVisible = false
                dbViewModel.search.value = ""
                searchWord = ""
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                isDataBaseVisible = !isDataBaseVisible
                isSearchVisible = false
                isAddNewScreenVisible = false
            }) {
                if (isDataBaseVisible) Text(text = "Hide all ")
                else Text(text = "Show all")

            }
            Button(onClick = {
                isAddNewScreenVisible = !isAddNewScreenVisible
                isSearchVisible = false
              //  dbViewModel.newNation.value = findNationId(nationality)
            }) {
                Text(text = "Add new")

            }
            Button(onClick = {
                dbViewModel.insert5Citizen()
            }) {
                Text(text = "Add 5 citizen")
            }
            Button(onClick = {
                dbViewModel.deleteAll()
            }) {
                Text(text = "Clear all")
            }
        }

        if (isAddNewScreenVisible) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newName1.value,
                        onValueChange = { dbViewModel.newName1.value = it},
                        label = {
                            Text(text = "Введите фамилию")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newName2.value,
                        onValueChange = { dbViewModel.newName2.value = it},
                        label = {
                            Text(text = "Введите имя")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newName3.value,
                        onValueChange = { dbViewModel.newName3.value = it},
                        label = {
                            Text(text = "Введите отчество")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newBirthday.value,
                        onValueChange = { dbViewModel.newBirthday.value = it},
                        label = {
                            Text(text = "Введите дату рождения")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    nationality = menuWithList(NationalityFieldName = "Национальность", nationView = "", itemList = nationalityList.map { it.nationName })

                    Button(
                        onClick = {
                            dbViewModel.newNation.value = findNationId(nationality)
                            dbViewModel.insertCitizen()
                            Toast.makeText(context,"Citizen added successfully", Toast.LENGTH_SHORT).show()
                            isAddNewScreenVisible = !isAddNewScreenVisible
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Сохранить")
                    }
                }
                }
        }

            if (isSearchVisible) {
                isDataBaseVisible = false
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(searchList) { citizen ->
                            CitizenCard(
                                citizen,{
                                    dbViewModel.citizenEntity = it
                                    dbViewModel.newName1.value = it.name1
                                    dbViewModel.newName2.value = it.name2
                                    dbViewModel.newName3.value = it.name3
                                    dbViewModel.newBirthday.value = it.birthday
                                    dbViewModel.newNation.value = it.nation
                                },
                                {
                                    dbViewModel.deleteCitizen(it)
                                }
                            )
                        }
                    }
            }

        if (isDataBaseVisible) {
            isSearchVisible = false
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(citizenList.value) { citizen ->
                    CitizenCard(
                        citizen,{
                            dbViewModel.citizenEntity = it
                            dbViewModel.newName1.value = it.name1
                            dbViewModel.newName2.value = it.name2
                            dbViewModel.newName3.value = it.name3
                            dbViewModel.newBirthday.value = it.birthday
                            dbViewModel.newNation.value = it.nation
                        },
                        {
                            dbViewModel.deleteCitizen(it)
                        }
                    )
                }
            }
        }
    }
}