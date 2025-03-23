package com.example.authenticationsignupnormal.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authenticationsignupnormal.FbViewModel
import com.example.authenticationsignupnormal.R

@Composable
fun SuccessScreen(navController: NavController, viewModel: FbViewModel) {
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }

    // Lấy thông tin từ Firestore khi màn hình được hiển thị
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser { email, password ->
            userEmail.value = email
            userPassword.value = password
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Email: ${userEmail.value}", fontSize = 20.sp)
        Text(text = "Password: ${userPassword.value}", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.logout()
            navController.navigate("login") { // Quay về màn hình đăng nhập
                popUpTo("success") { inclusive = true }
            }
        }) {
            Text("Sign Out")
        }
    }
}