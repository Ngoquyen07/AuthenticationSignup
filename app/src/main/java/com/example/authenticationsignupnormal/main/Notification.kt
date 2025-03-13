package com.example.authenticationsignupnormal.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.authenticationsignupnormal.FbViewModel

@Composable
fun Notification(viewModel: FbViewModel){
    val notificationState = viewModel.popupNotification.value
    val notificationMessage = notificationState?.getContentOrNull()
    if (notificationMessage != null){
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }

}