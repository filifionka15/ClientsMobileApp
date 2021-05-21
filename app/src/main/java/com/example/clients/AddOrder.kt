package com.example.clients




import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clients.models.Order
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_order.*
import java.text.SimpleDateFormat
import java.util.*


class AddOrder : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"
    var chosen:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_order)

        var arrayList : MutableList<String> = mutableListOf("Выбрать компанию")

        db.collection("clients")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    arrayList.add(document.get("company") as String)
                }
            }

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayList)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chosen = arrayList[position]
                Log.d(TAG, chosen!!)

            }
            }

    }
    fun onClickAdd(view: View?){
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


        if (chosen==null ){
            Toast.makeText(applicationContext,"Выберите компанию из списка",Toast.LENGTH_LONG).show()
            return
        }
        if (chosen=="Выбрать компанию" ) {
            Toast.makeText(applicationContext, "Выберите компанию из списка", Toast.LENGTH_LONG)
                .show()
            return
        }



        var cid:String? = null
        db.collection("clients")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("company") as String == chosen){
                        cid = document.id
                        var order = cid?.let {
                            Order(amount?.text.toString().toLong(),
                                it,Timestamp(SimpleDateFormat("dd/MM/yyyy").parse(date.text.toString())), name?.text.toString(), price?.text.toString().toLong())
                        }


                        if (order!=null) {
                            db.collection("orders").add(order)
                                .addOnSuccessListener {
                                    date.setText("")
                                    name.setText("")
                                    amount.setText("")
                                    price.setText("")
                                    Toast.makeText(applicationContext,"Успешно добавлено",Toast.LENGTH_LONG).show()

                                }
                                .addOnFailureListener {
                                    OnFailureListener {
                                        Toast.makeText(applicationContext,"failed",Toast.LENGTH_LONG).show()
                                    }
                                }

                        }
                    }

                }
            }



    }

}

    fun onClickBack(view: View?){
        val intent = Intent(this, Orders::class.java)
        startActivity(intent)
    }
}