package com.xyvona.pixelperfect.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadOptionsBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    isOpen: Boolean,
    onOptionSelected: (ImageDownloadOption) -> Unit,
    options: List<ImageDownloadOption> = ImageDownloadOption.entries,
) {
    if (isOpen) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState
        ) {
            options.forEach { option ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onOptionSelected(option) }
                        .padding(16.dp)
                ) {
                    Text(text = option.label, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

enum class ImageDownloadOption(val label: String) {
    SMALL(label = "Download Small Size"),
    MEDIUM(label = "Download Medium Size"),
    ORIGINAL(label = "Download Original Size"),
}