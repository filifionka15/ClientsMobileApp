package com.example.clients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.client_info.*


class ClientInfo : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {
        db.collection("clients")
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_info)

        val docRef = db.collection("clients").document(id.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val client: String = String()
                    client.plus(document.data?.getValue("company")).plus(document.data?.getValue("name")).plus(document.data?.getValue("email"))
                    txt.text = client
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        //txt.text = id
    }
}