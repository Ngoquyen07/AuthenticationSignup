package com.example.authenticationsignupnormal.auth

import android.graphics.drawable.ScaleDrawable
import android.telecom.Connection.RttModifyStatus
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authenticationsignupnormal.DestinationScreen
import com.example.authenticationsignupnormal.FbViewModel
import com.example.authenticationsignupnormal.R

@Composable
fun MainScreen(navController: NavController , viewModel: FbViewModel){
    // navController dùng để quản lý điều hướng giữa các act frag ... chứ ko phải là giao diện
    Image(
        painter = painterResource(id = R.drawable.blue),
        contentDescription = "background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        Image(
            painter = painterResource(id  = R.drawable.happy),
            contentDescription = "happy",
            Modifier.size(200.dp)
        )
        Text(
            text = "Welcome",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Yellow,Color.White,Color.Yellow)
                    )
                )
        ){
            Button(
                onClick = {
                    navController.navigate(DestinationScreen.Signup.router)
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text =  "Sign up",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )

            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Yellow,Color.White,Color.Yellow)
                    )
                )
        ){
            Button(
                onClick = {
                    navController.navigate(DestinationScreen.Login.router)
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text =  "Login",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
        }
    }
}