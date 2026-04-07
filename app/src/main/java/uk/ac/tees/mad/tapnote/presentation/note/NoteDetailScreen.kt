package uk.ac.tees.mad.tapnote.presentation.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteDetailViewModel,
    noteId: Long,
    onBack: () -> Unit
) {
    val note by viewModel.note.collectAsState()

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    var text by remember(note?.content) {
        mutableStateOf(note?.content ?: "")
    }

    note?.let {
        NoteDetailContent(
            note = it,
            text = text,
            onTextChange = { text = it },
            onSave = {
                viewModel.save(text)
                onBack()
            },
            onDelete = {
                viewModel.delete()
                onBack()
            },
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailPreview() {
    TapNoteTheme {
        NoteDetailContent(
            note = NoteDetailUiModel(
                id = 1,
                content = "This is the full content of a note. It can span multiple lines and is shown completely on this screen.",
                timestamp = "Created on 12 Feb 2026, 9:45 AM"
            ),
            text = "This is the full content of a note. It can span multiple lines and is shown completely on this screen.",
            onTextChange = {},
            onSave = {},
            onDelete = {}
        )
    }
}
