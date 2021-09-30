package dev.tindallia.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import registration.R

class LoginActivity() : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()){
            res -> this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createSignInIntent()
    }

    private fun createSignInIntent(){
        val loginProviders = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.Theme_Registration_Login)
            .setAvailableProviders(loginProviders)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult){
        val response = result.idpResponse
        if(result.resultCode == RESULT_OK ){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            //val user = FirebaseAuth.getInstance().currentUser
        }else{
            response?.error?.errorCode?.let { Toast.makeText(this, it,Toast.LENGTH_LONG).show() }
        }
    }
}