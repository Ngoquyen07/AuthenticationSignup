package com.example.authenticationsignupnormal

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authenticationsignupnormal.auth.LoginScreen
import com.example.authenticationsignupnormal.auth.MainScreen
import com.example.authenticationsignupnormal.auth.SignUpScreen
import com.example.authenticationsignupnormal.auth.SuccessScreen
import com.example.authenticationsignupnormal.main.Notification
import com.example.authenticationsignupnormal.ui.theme.AuthenticationSignupNormalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // Tắt decorFitsSystemWindows để giao diện tràn toàn màn hình
                WindowCompat.setDecorFitsSystemWindows(this, false)

                val controller = WindowInsetsControllerCompat(this, decorView)

                // Đổi màu nền Status Bar và Navigation Bar
                statusBarColor = Color.BLACK
                navigationBarColor = Color.BLACK

                // Điều chỉnh màu icon trên thanh trạng thái
                controller.isAppearanceLightStatusBars = false  // `false` = icon trắng trên nền tối
                controller.isAppearanceLightNavigationBars = false // `false` = icon trắng trên nav bar tối
            } else {
                // Cách cũ cho Android 11 trở xuống
                statusBarColor = Color.BLACK
                navigationBarColor = Color.BLACK
            }
        }

        setContent {
            AuthenticationSignupNormalTheme {
                AuthenticationApp()
            }
        }
    }
}
//Định nghĩa các destination (điểm đến) trong navigation graph.
//Mỗi đối tượng (Main, Login, Signup, Success) chứa một chuỗi định danh route, dùng để điều hướng.
sealed class DestinationScreen(val router: String){
    object  Main : DestinationScreen("main")
    object  Login : DestinationScreen("login")
    object  Signup : DestinationScreen("signup")
    object  Success : DestinationScreen("success")
}

@Composable
fun AuthenticationApp(){
    val vm  = hiltViewModel<FbViewModel>() // khởi tạo viewmodel với hilt ; bên trong <> là lớp của đối tượng ; ở đây là FbViewModel đã được xây dựng
                                            // instance của vm là firebase.auth
    val navController = rememberNavController() // khởi tạo navcontroller , quản lý điều hướng
    //Giả định đây là một composable hiển thị thông báo (popup hoặc snackbar) dựa trên trạng thái từ ViewModel. ko hiểu lắm
    Notification(viewModel = vm)
    // gọi đơn giản là xác ịnh màn hình hiển thị , mặc ịnh là main , mỗi 1 composable tương ứng 1 màn hình.
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Main.router
    ) {
        composable(DestinationScreen.Main.router) {
            MainScreen(navController, vm)
        }
        composable(DestinationScreen.Login.router) {
            LoginScreen(navController, vm)
        }
        composable(DestinationScreen.Signup.router) {
            SignUpScreen(navController, vm)
        }
        composable(DestinationScreen.Success.router) {
            SuccessScreen(navController, vm)
        }
    }
}

