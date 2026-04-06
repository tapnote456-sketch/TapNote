package uk.ac.tees.mad.tapnote.presentation.settings

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onLogout: () -> Unit
) {
    SettingsContent(
        hapticEnabled = viewModel.hapticEnabled,
        onToggleHaptic = { viewModel.hapticEnabled = it },
        onClearAll = { viewModel.clearAllNotes() },
        onLogout = onLogout
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    TapNoteTheme {
        SettingsContent(
            hapticEnabled = true,
            onToggleHaptic = {},
            onClearAll = {},
            onLogout = {}
        )
    }
}