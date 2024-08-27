package com.xyvona.pixelperfect.presentation.util

import android.app.Activity
import android.app.ProgressDialog.show
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun rememberWindowInsetsController(): WindowInsetsControllerCompat {
    val window = with(LocalContext.current as Activity) { return@with window }
    return remember {
        WindowCompat.getInsetsController(window, window.decorView)
    }
}

fun WindowInsetsControllerCompat.toggleStatusBar(show: Boolean) {
    if (show) show(WindowInsetsCompat.Type.systemBars())
    else hide(WindowInsetsCompat.Type.systemBars())
}