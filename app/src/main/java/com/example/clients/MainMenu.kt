package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_menu.*


class MainMenu : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        updateUI()


    }

    fun onClickProducts(view: View?){
        startActivity(Intent(this, Products::class.java))
    }


    fun onClickLogin(view: View?) {
        startActivity(Intent(this, Login::class.java))
    }
    fun onClickClients(view: View?) {
        startActivity(Intent(this, Clients::class.java))
    }
    fun onClickOrders(view: View?) {
        startActivity(Intent(this, Orders::class.java))
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
            clients_button.visibility = Button.INVISIBLE
            orders_button.visibility = Button.INVISIBLE
            db.collection("managers")
                .whereEqualTo("managerID",user?.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, user?.uid)
                        Log.d(TAG, document.get("managerID").toString())
                        Log.d(TAG, "${document.id} => ${document.data}")
                        clients_button.visibility = Button.VISIBLE
                        orders_button.visibility = Button.VISIBLE
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                    clients_button.visibility = Button.INVISIBLE
                    orders_button.visibility = Button.INVISIBLE
                }

        } else {
            // No user is signed in
            login_button.visibility = Button.VISIBLE
            logout_button.visibility = Button.INVISIBLE
            clients_button.visibility = Button.INVISIBLE
            orders_button.visibility = Button.INVISIBLE

        }



    }

}