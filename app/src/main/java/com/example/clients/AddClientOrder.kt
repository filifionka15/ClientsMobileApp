package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.clients.models.Client
import com.example.clients.models.Order
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_client_order.*
import kotlinx.android.synthetic.main.add_client_order.amount
import kotlinx.android.synthetic.main.add_client_order.date
import kotlinx.android.synthetic.main.add_client_order.name
import kotlinx.android.synthetic.main.add_client_order.price
import kotlinx.android.synthetic.main.add_order.*
import kotlinx.android.synthetic.main.client_info.*
import java.text.SimpleDateFormat
import java.util.*

class AddClientOrder : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_client_order)
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("clients").document(id.toString())
        Log.d(TAG, id.toString())


        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    client.text = document.get("company").toString()
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun onClickAdd(view: View?){
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        Log.d(TAG, id.toString())
        if (name?.text.toString().isEmpty()) {
            name?.error = "Пожалуйста, введите название товара"
            name?.requestFocus()
            return
        }

        if (date?.text.toString().isEmpty()) {
            date?.error = "Пожалуйста, введите дату"
            date?.requestFocus()
            return
        }

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

        if (date?.text.toString().isNotEmpty()) {
            var n : Date? = null
            try {
                n = SimpleDateFormat("dd/MM/yyyy")?.parse(date?.text?.toString())
            } catch (e: NumberFormatException) {
                date?.error = "Пожалуйста, введите дату в формате dd/MM/yyyy"
                date?.requestFocus()
                return
            }

        }

        var order = Order(amount?.text.toString().toLong(), id.toString(), Timestamp(SimpleDateFormat("dd/MM/yyyy").parse(date.text.toString())), name?.text.toString(), price?.text.toString().toLong())

        if (order!=null) {
            db.collection("orders").add(order)
                .addOnSuccessListener {
                    Log.d(TAG, order.toString())
                    date.setText("")
                    name.setText("")
                    amount.setText("")
                    price.setText("")
                    Toast.makeText(applicationContext,"Успешно добавлено",
                        Toast.LENGTH_LONG).show()

                }
                .addOnFailureListener {
                    OnFailureListener {
                        Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }

    fun onClickBack(view: View?){
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val intent = Intent(this, ClientInfo::class.java).apply {
            putExtra(
                AlarmClock.EXTRA_MESSAGE, id
            )
        }
        startActivity(intent)
    }
}




