package com.example.clients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.products.*

class Products : AppCompatActivity() {
    private val db = Firebase.firestore
    private val TAG = "MyLogger"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.products)
        readProducts()


    }
    private fun readProducts(){
        db.collection("products")
            .get()
            .addOnCompleteListener{
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful){
                    for (document in it.result!!){
                        result.append(document.data.getValue("name")).append("\n")
                            .append("\t").append("Цена: ").append(document.data.getValue("price")).append("\n")
                            .append("\t").append("Бренд: ").append(document.data.getValue("brand")).append("\n")
                            .append("\t").append("Объем: ").append(document.data.getValue("size")).append("\n")
                            .append("\t").append("В наличии, шт: ").append(document.data.getValue("quantity")).append("\n")
                            .append("\t").append("Описание: ").append(document.data.getValue("description")).append("\n\n")
                    }
                    textProducts.text = result
                }
            }
    }
}