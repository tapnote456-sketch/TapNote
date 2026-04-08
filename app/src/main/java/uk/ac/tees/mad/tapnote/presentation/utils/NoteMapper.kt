package uk.ac.tees.mad.tapnote.presentation.utils

import uk.ac.tees.mad.tapnote.data.local.entity.NoteEntity
import uk.ac.tees.mad.tapnote.presentation.home.NoteUiModel
import uk.ac.tees.mad.tapnote.presentation.note.NoteDetailUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NoteEntity.toUiModel(): NoteUiModel {
    val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return NoteUiModel(
        id = id,
        content = content,
        timestamp = formatter.format(Date(timestamp))
    )
}

fun NoteEntity.toDetailUi(): NoteDetailUiModel {
    val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return NoteDetailUiModel(
        id = id,
        content = content,
        timestamp = formatter.format(Date(timestamp))
    )
}
