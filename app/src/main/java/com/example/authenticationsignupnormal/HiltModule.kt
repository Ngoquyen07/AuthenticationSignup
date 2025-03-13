package com.example.authenticationsignupnormal

import com.google.api.Authentication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


// Mô-đun này cung cấp một instance duy nhất của FirebaseAuth (Firebase.auth) (instance có thể hiểu là 1 đối tượng củ thể của lớp đó)
// Khi ViewModel cần FirebaseAuth, Hilt sẽ tự động inject nó.
@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    //@provides : đây khai báo hàm cung cấp instance cho class khác
    @Provides
    fun provideAuthentication() : FirebaseAuth = Firebase.auth
}