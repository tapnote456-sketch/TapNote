package uk.ac.tees.mad.tapnote.presentation.quicknote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun QuickNoteContent(
    text: String,
    onTextChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {

            Box(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "TapNote",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Quick Note",
                        style = MaterialTheme.typography.titleMedium
                    )

                    OutlinedTextField(
                        value = text,
                        onValueChange = onTextChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 180.dp)
                            .focusRequester(focusRequester),
                        placeholder = { Text("Write something…") }
                    )

                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = text.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}