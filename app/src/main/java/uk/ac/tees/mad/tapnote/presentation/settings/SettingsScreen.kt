package uk.ac.tees.mad.tapnote.presentation.settings

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.tapnote.ui.theme.TapNoteTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    onLogout: () -> Unit
) {
    val hapticEnabled by viewModel.hapticEnabled.collectAsState()
    val shakeEnabled by viewModel.shakeEnabled.collectAsState()
    val shakeSensitivity by viewModel.shakeSensitivity.collectAsState()

    SettingsContent(
        hapticEnabled = hapticEnabled,
        shakeEnabled = shakeEnabled,
        shakeSensitivity = shakeSensitivity,
        onToggleHaptic = viewModel::setHapticEnabled,
        onToggleShake = viewModel::setShakeEnabled,
        onSensitivityChange = viewModel::setShakeSensitivity,
        onClearAll = viewModel::clearAllNotes,
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