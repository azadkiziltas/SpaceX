package com.redstoneapps.spacex.view.registerpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.btk.gun2.util.extentions.showToast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.redstoneapps.spacex.R
import com.redstoneapps.spacex.databinding.ActivityRegisterPageBinding
import com.redstoneapps.spacex.view.loginpage.LoginPageActivity

class RegisterPageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var TAG = "___"
    private lateinit var binding: ActivityRegisterPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = Firebase.auth

        binding.apply {
            registerButton.setOnClickListener {
                if (validateRegistration(
                        email = emailTextInput.text.toString(),
                        password = passwordTextInput.text.toString(),
                        rePassword = confirmPasswordTextInput.text.toString(),
                    )
                ) {
                    register(
                        email = emailTextInput.text.toString(),
                        password = passwordTextInput.text.toString(),
                    )
                }
            }
            backButton.setOnClickListener {
                onBackPressed()
            }
            emailTextInput.setOnFocusChangeListener { view, b ->
                if (b) emailTextInput.background =
                    getDrawable(R.drawable.border_edittext_small_shape_focus)
                else emailTextInput.background = getDrawable(R.drawable.border_edittext_small_shape)
            }
            passwordTextInput.setOnFocusChangeListener { view, b ->
                if (b) passwordTextInput.background =
                    getDrawable(R.drawable.border_edittext_small_shape_focus)
                else passwordTextInput.background =
                    getDrawable(R.drawable.border_edittext_small_shape)
            }
            confirmPasswordTextInput.setOnFocusChangeListener { view, b ->
                if (b) confirmPasswordTextInput.background =
                    getDrawable(R.drawable.border_edittext_small_shape_focus)
                else confirmPasswordTextInput.background =
                    getDrawable(R.drawable.border_edittext_small_shape)
            }
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    showToast("Register Successful")
                    val intent = Intent(this@RegisterPageActivity, LoginPageActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    showToast("Authentication failed.")
                }
            }
    }

    fun validateRegistration(email: String, password: String, rePassword: String): Boolean {


        val emailRegex = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$")
        if (!email.matches(emailRegex)) {
            showToast("Invalid e-mail")
            binding.emailTextInput.background =
                getDrawable(R.drawable.border_edittext_small_shape_focus)
            return false
        }
        if (password.length < 8) {
            showToast("Password must be at least 8 digits")
            binding.passwordTextInput.background =
                getDrawable(R.drawable.border_edittext_small_shape_focus)
            binding.confirmPasswordTextInput.background =
                getDrawable(R.drawable.border_edittext_small_shape_focus)
            return false
        }

        if (password != rePassword) {
            println("$password - $rePassword")
            binding.passwordTextInput.background =
                getDrawable(R.drawable.border_edittext_small_shape_focus)
            binding.confirmPasswordTextInput.background =
                getDrawable(R.drawable.border_edittext_small_shape_focus)
            return false
        }

        return true
    }
}