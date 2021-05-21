package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.clients.models.Client
import com.example.clients.models.Order
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class Orders : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    private val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders)

        val stk = findViewById<View>(R.id.table_orders) as TableLayout
        val tbrow0 = TableRow(this)
        val tv0 = TextView(this)
        tv0.text = " No "
        tbrow0.addView(tv0)
        val tv1 = TextView(this)
        tv1.text = " Компания "
        tbrow0.addView(tv1)
        val tv2 = TextView(this)
        tv2.text = " Мейл "
        tbrow0.addView(tv2)
        val tv3 = TextView(this)
        tv3.text = " Имя "
        tbrow0.addView(tv3)
        stk.addView(tbrow0)

        readOrders(stk)
    }

    private fun readOrders(stk: TableLayout){


        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                var no = 1
                for (document in result) {
                    var order = Order(document.get("amount") as Long, document.get("clientID") as String, document.get("date") as Timestamp, document.get("name") as String, document.get("price") as Long)
                    val tbrow = TableRow(this)

                    val t1v = TextView(this)
                    t1v.text = no.toString() +" "
                    tbrow.addView(t1v)

                    val t2v = TextView(this)
                    t2v.text = simpleDateFormat.format(order.date.toDate())+" "
                    tbrow.addView(t2v)

                    val t3v = TextView(this)
                    t3v.text = order.amount.toString() +" "
                    tbrow.addView(t3v)

                    val t4v = Button(this)
                    t4v.text = "Подробнее"
                    tbrow.addView(t4v)
                    t4v.setOnClickListener{
                        //val intent = Intent(this, OrderInfo::class.java).apply { putExtra(
                            //AlarmClock.EXTRA_MESSAGE, document.id) }
                        val intent = Intent(this, OrderInfo::class.java)
                        intent.putExtra("order", document.id)
                        intent.putExtra("client", "")
                        startActivity(intent)


                    }
                    //t4v.id = no
                    //Log.d(TAG, t4v.id.toString())
                    stk.addView(tbrow)
                    no += 1
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
    fun onClickAddOrder(view: View?){
        val intent = Intent(this, AddOrder::class.java)
        startActivity(intent)
    }
    fun onClickBack(view: View?) {
        startActivity(Intent(this, MainMenu::class.java))
    }

}