package com.example.clients

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_menu.*


class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        updateUI()
    }


    fun onClickLogin(view: View?) {
        startActivity(Intent(this, Login::class.java))
    }

    fun onClickLogout (view: View?) {
        Firebase.auth.signOut()
        updateUI()
    }

    private fun updateUI() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            login_button.visibility = Button.INVISIBLE
            logout_button.visibility = Button.VISIBLE

        } else {
            // No user is signed in
            login_button.visibility = Button.VISIBLE
            logout_button.visibility = Button.INVISIBLE

        }
    }

}