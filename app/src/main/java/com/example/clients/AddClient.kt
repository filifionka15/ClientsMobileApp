package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.clients.models.Client
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_client.*


class AddClient : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_client)
    }
    fun onClickAdd(view: View?){
        if (email?.text.toString().isEmpty()) {
            email?.error = "Пожалуйста, введите почту"
            email?.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email?.text.toString()).matches()) {
            email?.error = "Пожалуйста, введите корректную почту"
            email?.requestFocus()
            return
        }

        if (name?.text.toString().isEmpty()) {
            name?.error = "Пожалуйста, введите ФИО"
            name?.requestFocus()
            return
        }

        if (company?.text.toString().isEmpty()) {
            company?.error = "Пожалуйста, введите название компании"
            company?.requestFocus()
            return
        }

        var client = Client(company.text.toString(), email.text.toString(),name.text.toString())
        if (client!=null) {
            db.collection("clients").add(client)
                .addOnSuccessListener {
                        company.setText("")
                        name.setText("")
                        email.setText("")
                        Toast.makeText(applicationContext,"Успешно добавлено",Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener {
                    OnFailureListener {
                        Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                    }
                }

        }
    }

    fun onClickBack(view: View?){

        val intent = Intent(this, Clients::class.java)
        startActivity(intent)
    }
}