package uk.ac.tees.mad.tapnote.presentation.settings

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    onLogout: () -> Unit
) {
    SettingsContent(
        hapticEnabled = viewModel.hapticEnabled,
        shakeEnabled = viewModel.shakeEnabled,
        shakeSensitivity = viewModel.shakeSensitivity,
        onToggleHaptic = { viewModel.hapticEnabled = it },
        onToggleShake = { viewModel.shakeEnabled = it },
        onSensitivityChange = { viewModel.shakeSensitivity = it },
        onClearAll = { viewModel.clearAllNotes() },
        onLogout = onLogout,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    TapNoteTheme {
        SettingsContent(
            hapticEnabled = true,
            shakeEnabled = true,
            shakeSensitivity = 13f,
            onToggleHaptic = {},
            onToggleShake = {},
            onSensitivityChange = {},
            onClearAll = {},
            onLogout = {}
        )
    }
}