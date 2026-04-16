package uk.ac.tees.mad.tapnote.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun SettingsContent(
    hapticEnabled: Boolean,
    shakeEnabled: Boolean,
    shakeSensitivity: Float,
    onToggleHaptic: (Boolean) -> Unit,
    onToggleShake: (Boolean) -> Unit,
    onSensitivityChange: (Float) -> Unit,
    onClearAll: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showClearDialog by remember { mutableStateOf(false) }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
            shadowElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineSmall
                )

                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Haptic feedback")
                        Switch(
                            checked = hapticEnabled,
                            onCheckedChange = onToggleHaptic
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Shake to add note")
                        Switch(
                            checked = shakeEnabled,
                            onCheckedChange = onToggleShake
                        )
                    }

                    Column {
                        Text("Shake sensitivity")
                        Slider(
                            value = shakeSensitivity,
                            onValueChange = onSensitivityChange,
                            valueRange = 8f..20f
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { showClearDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Clear all notes")
                }

                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Logout")
                }
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showClearDialog = false
                        onClearAll()
                    }
                ) {
                    Text("Delete all", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancel")
                }
            },
            text = { Text("Delete ALL notes permanently?") },
            shape = RoundedCornerShape(20.dp),
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}