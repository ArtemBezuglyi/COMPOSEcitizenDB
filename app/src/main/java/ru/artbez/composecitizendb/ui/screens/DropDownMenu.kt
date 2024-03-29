package ru.artbez.composecitizendb.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun menuWithList(NationalityFieldName : String, nationView: String, itemList : List<String>) : String {

    var menuHidden by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(nationView) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val menuArrowIcon = if (menuHidden) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    OutlinedTextField(
        value = selectedText,
        onValueChange = { selectedText = it },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
        label = {
            Text(
                text = NationalityFieldName,
                fontWeight = FontWeight.Bold
            )
        },
        trailingIcon = {
            Icon(
                imageVector = menuArrowIcon,
                contentDescription = "Icon that show/hide menu list",
                modifier = Modifier.clickable { menuHidden = !menuHidden })
        },
        enabled = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = LocalContentColor.current.copy(DefaultAlpha),
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = DefaultAlpha),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(DefaultAlpha),
            backgroundColor = Color.White
        )
    )
    DropdownMenu(
        expanded = menuHidden,
        onDismissRequest = { menuHidden = false },
        modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() - 50.dp})
    ) {
        itemList.forEach { item ->
            DropdownMenuItem(text = {
                Text(
                    text = item,
                    fontWeight = FontWeight.Bold
                )
            }, onClick = {
                selectedText = item
                menuHidden = false
            })

        }
    }
    return selectedText
}