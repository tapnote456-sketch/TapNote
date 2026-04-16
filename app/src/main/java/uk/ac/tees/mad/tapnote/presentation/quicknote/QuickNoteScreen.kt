package uk.ac.tees.mad.tapnote.presentation.quicknote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun QuickNoteScreen(
    viewModel: QuickNoteViewModel = viewModel(),
    onClose: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.onPopupOpened()
    }

    QuickNoteContent(
        text = text,
        onTextChange = { text = it },
        onSaveClick = {
            viewModel.saveNote(text) {
                onClose()
            }
        },
        onDismiss = onClose
    )
}

@Preview(showBackground = true)
@Composable
private fun QuickNoteScreenPreview() {
    TapNoteTheme {
        QuickNoteContent(
            text = "Sample quick note",
            onTextChange = {},
            onSaveClick = {},
            onDismiss = {}
        )
    }
}