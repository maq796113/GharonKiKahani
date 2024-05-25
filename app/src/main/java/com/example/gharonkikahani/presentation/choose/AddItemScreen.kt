package com.example.gharonkikahani.presentation.add_items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(navController: NavHostController) {
    var items by remember { mutableStateOf(listOf(ItemRow())) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Items") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Item", modifier = Modifier.weight(0.5f), fontSize = 20.sp)
                    Text("Quantity", modifier = Modifier.weight(0.25f), fontSize = 20.sp)
                    Text("Weight", modifier = Modifier.weight(0.25f), fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                items.forEachIndexed { index, item ->
                    ItemRow(
                        item = item,
                        onItemChange = { newItem ->
                            items = items.toMutableList().apply { set(index, newItem) }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        items = items + ItemRow()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Add Item")
                }
            }
        }
    )
}

@Composable
fun ItemRow(
    item: ItemRow,
    onItemChange: (ItemRow) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(vertical = 4.dp)) {
        var itemName by remember { mutableStateOf(item.itemName) }
        var quantity by remember { mutableStateOf(item.quantity) }
        var weight by remember { mutableStateOf(item.weight) }

        BasicTextField(
            value = itemName,
            onValueChange = {
                itemName = it
                onItemChange(item.copy(itemName = it))
            },
            modifier = Modifier.weight(0.5f).padding(4.dp)
        )
        BasicTextField(
            value = quantity,
            onValueChange = {
                quantity = it
                onItemChange(item.copy(quantity = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(0.25f).padding(4.dp)
        )
        BasicTextField(
            value = weight,
            onValueChange = {
                weight = it
                onItemChange(item.copy(weight = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(0.25f).padding(4.dp)
        )
    }
}

data class ItemRow(
    val itemName: String = "",
    val quantity: String = "",
    val weight: String = ""
)
