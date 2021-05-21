package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



import kotlinx.android.synthetic.main.edit_order.*


import java.text.SimpleDateFormat
import java.util.*

class EditOrder : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    private val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_order)
        val orderId = intent.getStringExtra("order")

        val docRef = db.collection("orders").document(orderId.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    simpleDateFormat.format((document.get("date") as com.google.firebase.Timestamp).toDate())
                    date.setText(simpleDateFormat.format((document.get("date") as com.google.firebase.Timestamp).toDate()))
                    name.setText(document.get("name").toString())
                    amount.setText(document.get("amount").toString())
                    price.setText(document.get("price").toString())
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun onClickUpdateDate(view: View){
        if (date?.text.toString().isEmpty()) {
            date?.error = "Пожалуйста, введите дату"
            date?.requestFocus()
            return
        }
        if (date?.text.toString().isNotEmpty()) {
            var n : Date? = null
            try {
                n = SimpleDateFormat("dd/MM/yyyy")?.parse(date?.text?.toString())
            } catch (e: NumberFormatException) {
                date?.error = "Пожалуйста, введите дату в формате dd/MM/yyyy"
                date?.requestFocus()
                return
            }
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("orders").document(id.toString())
        docRef.update("date", Timestamp(SimpleDateFormat("dd/MM/yyyy").parse(date.text.toString())))
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"Дата отредактирована", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                    Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                }
            }

    }
    }
    fun onClickUpdateName(view: View){
        if (name?.text.toString().isEmpty()) {
            name?.error = "Пожалуйста, введите название товара"
            name?.requestFocus()
            return
        }
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("orders").document(id.toString())
        docRef.update("name",name.text.toString())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"Название отредактировано", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                    Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                }
            }
    }
    fun onClickUpdateAmount(view: View){
        if (amount?.text.toString().isEmpty()) {
            amount?.error = "Пожалуйста, введите количество товара"
            amount?.requestFocus()
            return
        }

        if (amount?.text.toString().isNotEmpty()) {
            var value = 0
            try {
                value = amount?.text.toString().toInt()
            } catch (e: NumberFormatException) {
                amount?.error = "Пожалуйста, введите количество числом"
                amount?.requestFocus()
                return
            }
        }

        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("orders").document(id.toString())
        docRef.update("amount",amount.text.toString().toLong())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"Количество отредактировано", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                    Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                }
            }
    }
    fun onClickUpdatePrice(view: View){
        if (price?.text.toString().isEmpty()) {
            price?.error = "Пожалуйста, введите цену товара"
            price?.requestFocus()
            return
        }


        if (price?.text.toString().isNotEmpty()) {
            var value = 0
            try {
                value = amount?.text.toString().toInt()
            } catch (e: NumberFormatException) {
                price?.error = "Пожалуйста, введите цену числом"
                price?.requestFocus()
                return
            }


        }
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("orders").document(id.toString())
        docRef.update("price",price?.text.toString().toLong())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(applicationContext,"Цена отредактирована", Toast.LENGTH_LONG).show()}

            .addOnFailureListener {
                OnFailureListener {
                    Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                }
            }

    }

    fun onClickBack(view: View){
        val orderId = intent.getStringExtra("order")
        val clientId = intent.getStringExtra("client")

            val intent = Intent(this, OrderInfo::class.java)
            intent.putExtra("order", orderId)
            intent.putExtra("client", clientId)
            startActivity(intent)
        }




}