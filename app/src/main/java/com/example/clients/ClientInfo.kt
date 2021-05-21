package com.example.clients

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.clients.models.Client
import com.example.clients.models.Order
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.util.FileUtil.delete
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import kotlinx.android.synthetic.main.client_info.*
import java.sql.Timestamp
import java.text.SimpleDateFormat




class ClientInfo : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    //val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        db.collection("clients")
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val docRef = db.collection("clients").document(id.toString())
        Log.d(TAG,  docRef.toString())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_info)

        Log.d(TAG,  id.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    var client = Client(document.get("company") as String, document.get("email") as String, document.get("name") as String)
                    var record: String = "Компания: "+ client.company+"\n"+
                            "Представитель компании: "+ client.name+"\n"+
                            "E-mail: "+client.email
                    txt.text = record
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        val stk = findViewById<View>(R.id.table_orders2) as TableLayout
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

        if (id != null) {
            readClients(stk,id)
        }
    }

    private fun readClients(stk: TableLayout, id: String){

        val clientId = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        db.collection("orders")
            .whereEqualTo("clientID",id)

            .get()
            .addOnSuccessListener { result ->
                var no = 1

                for (document in result) {
                    var order = Order(document.get("amount") as Long, document.get("clientID") as String, document.get("date") as com.google.firebase.Timestamp, document.get("name") as String, document.get("price") as Long)
                    val tbrow = TableRow(this)

                    val t1v = TextView(this)
                    t1v.text = no.toString()
                    Log.d(TAG, " ")
                    tbrow.addView(t1v)

                    val t2v = TextView(this)

                    t2v.text = simpleDateFormat.format(order.date.toDate())+" "

                    //Log.d(TAG, document.data.getValue("date").toString().dropLast(16).drop(18))

                    tbrow.addView(t2v)

                    val t3v = TextView(this)
                    t3v.text =  order.amount.toString()

                    Log.d(TAG, document.data.getValue("amount").toString())
                    tbrow.addView(t3v)

                    val t4v = Button(this)
                    t4v.text = "Подробнее"
                    tbrow.addView(t4v)
                    t4v.setOnClickListener {
                        val intent = Intent(this, OrderInfo::class.java)
                        intent.putExtra("order", document.id)
                        intent.putExtra("client", clientId)
                        startActivity(intent)
                    }
                    //t4v.id = no
                    //Log.d(TAG, t4v.id.toString())
                    stk.addView(tbrow)
                    no += 1
                    //}
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun onClickDelete(view: View?){
        val clientId = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы уверены, что хотите удалить клиента? Заказы данного клиента также будут удалены.")
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, id ->
                db.collection("clients").document(clientId.toString())
                    .delete()
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                db.collection("orders")
                    .whereEqualTo("clientID",clientId)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result){
                            db.collection("orders").document(document.id)
                                .delete()
                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                        }
                        }

                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
                finish()
                startActivity(Intent(this, Clients::class.java))


            }
            .setNegativeButton("Нет") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun onClickAdd(view: View?){
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val intent = Intent(this, AddClientOrder::class.java).apply {
            putExtra(
                AlarmClock.EXTRA_MESSAGE, id
            )
        }
        startActivity(intent)
    }
    fun onClickEdit(view: View?){
        val id = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val intent = Intent(this, EditClient::class.java).apply {
            putExtra(
                AlarmClock.EXTRA_MESSAGE, id
            )
        }
        startActivity(intent)
    }
    fun onClickBack(view: View?) {
        startActivity(Intent(this, Clients::class.java))
    }

}