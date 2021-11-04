package xyz.teamgravity.notetaker.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import xyz.teamgravity.notetaker.feature_note.domain.util.NoteOrder
import xyz.teamgravity.notetaker.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    noteOrder: NoteOrder,
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .testTag("order_section_test")
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title
            ) {
                onOrderChange(NoteOrder.Title(noteOrder.order))
            }
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date
            ) {
                onOrderChange(NoteOrder.Date(noteOrder.order))
            }
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color
            ) {
                onOrderChange(NoteOrder.Color(noteOrder.order))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.order is OrderType.Ascending
            ) {
                onOrderChange(noteOrder.copy(OrderType.Ascending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.order is OrderType.Descending
            ) {
                onOrderChange(noteOrder.copy(OrderType.Descending))
            }
        }
    }
}