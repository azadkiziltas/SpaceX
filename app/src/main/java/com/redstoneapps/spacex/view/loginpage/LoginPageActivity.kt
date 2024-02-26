package com.redstoneapps.spacex.view.loginpage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.btk.gun2.util.extentions.showToast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.redstoneapps.spacex.R
import com.redstoneapps.spacex.view.registerpage.RegisterPageActivity
import com.redstoneapps.spacex.constants.Constants.CLIENT_ID
import com.redstoneapps.spacex.constants.Constants.RC_SIGN_IN
import com.redstoneapps.spacex.databinding.ActivityLoginPageBinding
import com.redstoneapps.spacex.view.homepage.HomePageActivity

class LoginPageActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginPageBinding
    private var callbackManager = CallbackManager.Factory.create()
    var TAG = "___"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = FirebaseAuth.getInstance()

        binding.apply {
            loginButton.setOnClickListener {
                if (validateLogin(
                        email = emailTextInput.text.toString(),
                        password = passwordTextInput.text.toString()
                    )
                ) {
                    loginWithEmail(
                        email = emailTextInput.text.toString(),
                        password = passwordTextInput.text.toString()
                    )
                }
            }
            facebookIconButton.setOnClickListener {
                loginWithFacebook()
            }
            googleIconButton.setOnClickListener {
                loginWitgGoogle()
            }
            registerNowText.setOnClickListener {
                val intent = Intent(this@LoginPageActivity, RegisterPageActivity::class.java)
                startActivity(intent)
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
        }

    }

    private fun loginWithEmail(email: String, password: String) {
        Log.d(TAG, "loginWithEmail: $email - $password")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    startActivity(Intent(this, HomePageActivity::class.java))
                    finish()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun loginWitgGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {

            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, HomePageActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    showToast("Login Successful")
                    val user = auth.currentUser
                    startActivity(Intent(this, HomePageActivity::class.java))
                    finish()
                } else {
                    showToast("Authentication failed.")
                }
            }
    }

    private fun loginWithFacebook() {
        LoginManager.getInstance()
            .logInWithReadPermissions(
                this@LoginPageActivity,
                listOf("public_profile", "email")
            )
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            },
        )

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun validateLogin(email: String, password: String): Boolean {


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
            return false
        }

        return true
    }

}