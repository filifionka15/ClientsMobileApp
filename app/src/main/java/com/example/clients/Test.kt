package com.example.clients


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class Test : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val stk = findViewById<View>(R.id.table_main) as TableLayout
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

        readClients(stk)



    }


    private fun readClients(stk: TableLayout){


        db.collection("clients")
            .get()
            .addOnSuccessListener { result ->
                var no = 1
                for (document in result) {

                        val tbrow = TableRow(this)

                        val t1v = TextView(this)
                        t1v.text = no.toString() +" "
                        tbrow.addView(t1v)

                        val t2v = TextView(this)
                        t2v.text = document.data.getValue("company").toString()+" "
                        tbrow.addView(t2v)

                        val t3v = TextView(this)
                        t3v.text = document.data.getValue("email").toString()+" "
                        tbrow.addView(t3v)

                        val t4v = Button(this)
                        t4v.text = "Подробнее"
                        tbrow.addView(t4v)
                        t4v.setOnClickListener{
                            val intent = Intent(this, ClientInfo::class.java).apply { putExtra(EXTRA_MESSAGE, document.id) }
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

}
