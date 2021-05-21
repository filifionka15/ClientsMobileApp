package com.example.clients

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import com.example.clients.models.Client
import com.example.clients.models.Order
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.client_info.*
import kotlinx.android.synthetic.main.order_info.*
import java.text.SimpleDateFormat


class OrderInfo : AppCompatActivity() {
    private val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {

        db.collection("orders")
        val id = intent.getStringExtra("order")
        val docRef = db.collection("orders").document(id.toString())
        Log.d(TAG, docRef.toString())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_info)


        Log.d(TAG, id.toString())



        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var order = Order(
                        document.get("amount") as Long,
                        document.get("clientID") as String,
                        document.get("date") as com.google.firebase.Timestamp,
                        document.get("name") as String,
                        document.get("price") as Long
                    )
                    db.collection("clients").document(order.clientID).get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                var client = Client(
                                    document.get("company") as String,


                                    document.get("email") as String,
                                    document.get("name") as String

                                    )


                                var record: String =
                                    "Дата заказа: " + simpleDateFormat.format(order.date.toDate()) + "\n" +
                                            "Количество: " + order.amount + "\n" +
                                            "Название товара: " + order.name + "\n" +
                                            "Цена: " + order.price + "\n" +
                                            "Стоимость: " + (order.price * order.amount).toString() + "\n" +
                                            "Заказчик: " + client.company + "\n"

                                orderView.text = record
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            } else {
                                Log.d(TAG, "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }
                }


            }
    }

    fun onClickDelete(view: View?){
        //val orderId = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val orderId = intent.getStringExtra("order")
        val clientId = intent.getStringExtra("client")
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы уверены, что хотите удалить заказ?")
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, id ->
                db.collection("orders").document(orderId.toString())
                    .delete()
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                finish()
                if (clientId==""){
                startActivity(Intent(this, Orders::class.java))
                }
                else {
                    val intent = Intent(this, ClientInfo::class.java).apply {
                        putExtra(
                            AlarmClock.EXTRA_MESSAGE, clientId
                        )
                    }
                    startActivity(intent)
                }

            }
            .setNegativeButton("Нет") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
    fun onClickEdit(view: View?){
        val orderId = intent.getStringExtra("order")
        val clientId = intent.getStringExtra("client")
        val intent = Intent(this, EditOrder::class.java)
        intent.putExtra("order", orderId)
        intent.putExtra("client", clientId)
        startActivity(intent)
    }

    fun onClickBack(view: View?){
        val orderId = intent.getStringExtra("order")
        val clientId = intent.getStringExtra("client")
        if (clientId==""){
            startActivity(Intent(this, Orders::class.java))
        }
        else {
            val intent = Intent(this, ClientInfo::class.java).apply {
                putExtra(
                    AlarmClock.EXTRA_MESSAGE, clientId
                )
            }
            startActivity(intent)
        }
    }
}
