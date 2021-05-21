package com.example.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.clients.models.Product
import com.google.firebase.firestore.DocumentReference
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
                        var product = Product(document.get("brand") as String, document.get("description") as String, document.get("name") as String, document.get("price") as Long, document.get("quantity") as Long, document.get("size") as String)
                        result.append(document.data.getValue("name")).append("\n")
                            .append("\t").append("Цена: ").append(product.price).append("\n")
                            .append("\t").append("Бренд: ").append(product.brand).append("\n")
                            .append("\t").append("Объем: ").append(product.size).append("\n")
                            .append("\t").append("В наличии, шт: ").append(product.quantity).append("\n")
                            .append("\t").append("Описание: ").append(product.description).append("\n\n")
                    }
                    textProducts.text = result
                }
            }
    }
    fun onClickBack(view: View?) {
        startActivity(Intent(this, MainMenu::class.java))
    }
}