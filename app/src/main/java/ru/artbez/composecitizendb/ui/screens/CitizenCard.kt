package ru.artbez.composecitizendb.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.artbez.composecitizendb.DbViewModel
import ru.artbez.composecitizendb.data.NationList
import ru.artbez.composecitizendb.data.base.CitizenEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenCard(
    citizen: CitizenEntity,
    onClick: (CitizenEntity) -> Unit,
    onClickDelete: (CitizenEntity) -> Unit,
    dbViewModel: DbViewModel = viewModel(factory = DbViewModel.factory),
) {
    val context = LocalContext.current
    var isEditScreenVisible by remember { mutableStateOf(false) }

    fun findNationById(nationId: Int) : String? {
        val list = NationList.nationalityList
        val foundElement = list.find {it.id == nationId}
        return foundElement?.nationName
    }

    val nationality = findNationById(citizen.nation)!!

    Card(modifier = Modifier.fillMaxWidth().padding(2.dp).clickable {
        onClick(citizen)
        isEditScreenVisible = !isEditScreenVisible
    }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onClickDelete(citizen)
                        isEditScreenVisible = false
                        Toast.makeText(
                            context,
                            "Citizen deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                Column(
                    modifier = Modifier
                        //.padding(5.dp)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow,
                                visibilityThreshold = null
                            )
                        )
                ) {
                    Row {
                            findNationById(citizen.nation)
                            Text(
                                text = "${citizen.name1} ${citizen.name2} ${citizen.name3} \n ${citizen.birthday} \n ${findNationById(citizen.nation)}"
                            )
                    }
                }
                IconButton(onClick = { isEditScreenVisible = !isEditScreenVisible }) {
                    Icon(
                        imageVector = if (isEditScreenVisible) Icons.Filled.ArrowDropDown else Icons.Filled.Edit,
                        contentDescription = if (isEditScreenVisible) {
                            "R.string.show_less"
                        } else {
                            "R.string.show_more"
                        }
                    )
                }
            }

        if (isEditScreenVisible) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newName1.value,
                        onValueChange = { dbViewModel.newName1.value = it },
                        label = {
                            androidx.compose.material.Text(text = "Введите фамилию")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newName2.value,
                        onValueChange = { dbViewModel.newName2.value = it },
                        label = {
                            androidx.compose.material.Text(text = "Введите имя")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newName3.value,
                        onValueChange = { dbViewModel.newName3.value = it },
                        label = {
                            androidx.compose.material.Text(text = "Введите отчество")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dbViewModel.newBirthday.value,
                        onValueChange = { dbViewModel.newBirthday.value = it },
                        label = {
                            androidx.compose.material.Text(text = "Введите дату рождения")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                   menuWithList(NationalityFieldName = "Национальность", nationView = nationality, itemList = NationList.nationalityList.map { it.nationName })
                    Button(
                        onClick = {
                            isEditScreenVisible = false
                            dbViewModel.newNation.value = findNationId(nationality)!!
                            dbViewModel.insertCitizen()
                            Toast.makeText(
                                context,
                                "Citizen changed successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Сохранить")
                    }
                }
            }
        }
        }
    }

fun findNationId(nation: String) : Int? {
    val list = NationList.nationalityList
    val foundElement = list.find {it.nationName == nation}
    return foundElement?.id
}




