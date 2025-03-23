package com.example.authenticationsignupnormal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FbViewModel @Inject constructor( // kỹ thuật dependency inject : ở đây authentication được cung cấp từ bên ngoài (thng qua module) vào
                                        // contructor chứ đây ko phải là 1 thuộc tính của lớp. nhưng lớp lại có thể dùng nó như bình thường
                                        // FbViewModel ko bị phụ thuộc vào authentication
    val authentication: FirebaseAuth // hiltviewmodle chỉ cần tới module khi class nó cần ko tonfo tại @inject constructor beên trong ( tức  đây là những classmà hilt ko thể tự tạo instance )

): ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val signedIn = mutableStateOf(false) // ko thể dùng by remember vì nó chỉ tồn tại trong composable (viewmodel sống lâu hơn composable)
    val inProgress = mutableStateOf(false) // biểu tượng loadding (xoay load)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val _user = mutableStateOf<FirebaseUser?>(authentication.currentUser)
    lateinit var uid : String

    fun onSignup(email: String, password: String){
        inProgress.value = true
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    signedIn.value = true
                    uid = it.result.user?.uid ?: ""
                    val userData = hashMapOf(
                        "uid" to uid,
                        "email" to email,
                        "password" to password // ❗ Lưu mật khẩu dạng chuỗi gốc (plaintext)
                    )
                    db.collection("users").document(uid)
                        .set(userData)
                        .addOnSuccessListener {
                            println("User data saved to Firestore!")
                        }
                        .addOnFailureListener { e ->
                            println("Error saving user data: ${e.message}")
                        }
                    handleException(it.exception,"Signup successful")
                }else{
                    handleException(it.exception,"Signup failed")
                }
                inProgress.value = false
            }
    }
    fun login(email: String, password: String){
        inProgress.value = true
        authentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    signedIn.value = true
                    handleException(it.exception,"Login successful")
                }else{
                    handleException(it.exception,"Login failed")
                }
                inProgress.value = false
            }
    }
    fun handleException(e: Exception ? = null ,customMessage: String = ""){
        e?.printStackTrace()
        val errorMessage = e?.localizedMessage ?:""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage :  $errorMessage"
        popupNotification.value = Event(message)

    }
    fun logout(){
        authentication.signOut()
        signedIn.value = false
        _user.value = null
    }
    fun getCurrentUser(onResult: (String, String) -> Unit) {
        val user = authentication.currentUser
        user?.let {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    val email = document.getString("email") ?: "N/A"
                    val password = document.getString("password") ?: "N/A"
                    onResult(email, password)
                }
                .addOnFailureListener {
                    onResult("Error", "Error")
                }
        }
    }
}