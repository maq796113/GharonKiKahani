package com.example.gharonkikahani.presentation.manual_input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharonkikahani.states.IngredientState
import com.example.gharonkikahani.ui.components.RuniqueTextField
import com.example.gharonkikahani.ui.theme.Peach


@Composable
fun GridCellTable(
    ingredientState: IngredientState
) {
    val items = remember {
        SnapshotStateMap<Int, IngredientState>()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Add items", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Peach)
                .padding(8.dp)
        ) {
            Text("Items", modifier = Modifier.weight(1f))
            Text("Quantity", modifier = Modifier.weight(1f))
            Text("Weight", modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            RuniqueTextField(
                state = ingredientState.name,
                startIcon = null,
                endIcon = null,
                hint = "Item",
                title = null)

            RuniqueTextField(
                state = ingredientState.amount,
                startIcon = null,
                endIcon = null,
                hint = "Amount",
                title = null)

            RuniqueTextField(
                state = ingredientState.unit,
                startIcon = null,
                endIcon = null,
                hint = "Unit",
                title = null)

        }
        Button(
            onClick = {
                //append the entry to items
                items[items.size] = ingredientState

                //reset the state

            }
        ) {
            Text("➕ Add Item")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp
                        )

                ) {
                    Text(item.name, modifier = Modifier.weight(1f))
                    Text(item.amount, modifier = Modifier.weight(1f))
                    Text(item.unit, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


