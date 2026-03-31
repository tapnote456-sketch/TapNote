package uk.ac.tees.mad.tapnote.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddNoteClick: () -> Unit = {},
    onNoteClick: (NoteUiModel) -> Unit = {},
) {
    val notes by viewModel.notes.collectAsState()

    HomeContent(
        notes = notes,
        onAddNoteClick = onAddNoteClick,
        onNoteClick = onNoteClick,
        onDeleteNote = viewModel::deleteNote
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    TapNoteTheme {
        HomeContent(
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
            ),
            onAddNoteClick = {},
            onNoteClick = {},
            onDeleteNote = {}
        )
    }
}
