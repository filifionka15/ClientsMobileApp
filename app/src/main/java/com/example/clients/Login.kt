package com.example.clients

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*


class Login : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        if (auth!!.currentUser != null) {
            // User is signed in
            Firebase.auth.signOut()
        } else {
            // No user is signed in
        }
    }

    fun onClickLogin(view: View?) {
        if (email?.text.toString().isEmpty()) {
            email?.error = "Пожалуйста, введите свою почту"
            email?.requestFocus()
            return
        }

        if (password?.text.toString().isEmpty()) {
            password?.error = "Пожалуйста, введите пароль"
            password?.requestFocus()
            return
        }

        auth?.signInWithEmailAndPassword(email?.text.toString(), password?.text.toString())
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser
                    startActivity(Intent(this, MainMenu::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Ошибка входа. Попробуйте ещё раз",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun onClickRegistration(view: View?) {
        startActivity(Intent(this, Registration::class.java))
    }

    fun onClickBack(view: View?) {
        startActivity(Intent(this, MainMenu::class.java))
    }
}