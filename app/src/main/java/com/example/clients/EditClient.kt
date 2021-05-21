package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.clients.models.Client
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_client.*
import kotlinx.android.synthetic.main.edit_client.*
import kotlinx.android.synthetic.main.edit_client.company
import kotlinx.android.synthetic.main.edit_client.email
import kotlinx.android.synthetic.main.edit_client.name


class EditClient : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("clients").document(id.toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_client)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    company.setText(document.get("company") as String)
                    email.setText(document.get("email") as String)
                    name.setText(document.get("name") as String)
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    fun onClickUpdateCompany(view: View){
        if (company?.text.toString().isEmpty()) {
            company?.error = "Пожалуйста, введите название компании"
            company?.requestFocus()
            return
        }
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("clients").document(id.toString())
        docRef.update("company",company?.text.toString())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"Название компании отредактировано", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                    Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                }
            }

    }
    fun onClickUpdateEmail(view: View){
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
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("clients").document(id.toString())
        docRef.update("email",email.text.toString())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"Почта отредактирована", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                    Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                }
            }
    }
    fun onClickUpdateName(view: View){
        if (name?.text.toString().isEmpty()) {
            name?.error = "Пожалуйста, введите ФИО"
            name?.requestFocus()
            return
        }
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("clients").document(id.toString())
        docRef.update("name",name.text.toString())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"ФИО отредактировано", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                }
            }
    }
    fun onClickBack(view: View?){
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val intent = Intent(this, ClientInfo::class.java).apply { putExtra(AlarmClock.EXTRA_MESSAGE, id) }
        startActivity(intent)
    }
}