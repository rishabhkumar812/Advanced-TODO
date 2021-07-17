package com.projects.advancedtodo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.quickstart.auth.R


class FirebaseUIActivity : AppCompatActivity() {

    lateinit var googleSignInBtn: Button
    lateinit var noSignInBtn:Button
    lateinit var howToUseBtn:Button
    lateinit var howItWorksBtn:Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_ui)

        googleSignInBtn=findViewById(R.id.googleSignIn)
        noSignInBtn=findViewById(R.id.noSignIn)
        howToUseBtn=findViewById(R.id.howToUse)
        howItWorksBtn=findViewById(R.id.howItWorks)
        sharedPreferences=getSharedPreferences("User data",Context.MODE_PRIVATE)

        if(sharedPreferences.getBoolean("isLoggedIn",false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun signInUsingGoogle(view: View){
        createSignInIntent()
    }

    fun useWithNoSignIn(view:View) {
        /**FirebaseAuth.getInstance().currentUser should be null*/
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    fun howToUse(view:View) {
        //TODO how to use hyperlink
    }

    fun howDoesItWork(view: View) {
        //TODO how does it work hyperlink
        //privacyAndTerms()
        //themeAndLogo()
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                if(user!=null){
                    val userId= user.uid
                    val userEmail=user.email
                    sharedPreferences.edit().putString("userID",userId).apply()//TODO also have AuthUI.getInstance()
                    sharedPreferences.edit().putString("userEmail",userEmail).apply() //TODO may not use
                    sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                if (response != null) {
                    response.error?.let { Toast.makeText(this, it.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    // [END auth_fui_result]

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_delete]
    }

    /*private fun themeAndLogo() {
        val providers = emptyList<AuthUI.IdpConfig>()

        // [START auth_fui_theme_logo]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.todo_icon_round) // Set logo drawable
                .setTheme(R.style.MySuperAppTheme) // Set theme
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_theme_logo]
    }*/

  /*  private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_pp_tos]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                    .setLogo(R.mipmap.todo_icon_round)
                    .setTheme(R.style.MySuperAppTheme)
                .setTosAndPrivacyPolicyUrls(
                    "https://github.com/rishabhkumar812",
                    "https://github.com/rishabhkumar812")
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_pp_tos]
    }*/

    companion object {

        private const val RC_SIGN_IN = 123
    }
}