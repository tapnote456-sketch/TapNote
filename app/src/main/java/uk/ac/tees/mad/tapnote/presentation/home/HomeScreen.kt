package uk.ac.tees.mad.tapnote.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.tapnote.presentation.note.NoteUiModel
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun HomeScreen(
    notes: List<NoteUiModel> = emptyList(),
    onAddNoteClick: () -> Unit = {},
    onNoteClick: (NoteUiModel) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Text(
            text = "Your Notes",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Shake phone to add a quick note",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        if (notes.isEmpty()) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No notes yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Shake your phone or tap Add Note",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }

        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onClick = { onNoteClick(note) }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onAddNoteClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TapNoteTheme {
        HomeScreen(
            notes = listOf(
                NoteUiModel(
                    id = 1,
                    content = "Buy groceries and remember to check discounts.",
                    timestamp = "Today, 10:32 AM"
                ),
                NoteUiModel(
                    id = 2,
                    content = "Project idea: hardware-triggered notes using sensors.",
                    timestamp = "Yesterday, 8:15 PM"
                )
            )
        )
    }
}
