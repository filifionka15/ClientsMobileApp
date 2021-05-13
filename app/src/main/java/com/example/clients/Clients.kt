package com.example.clients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow

class Clients : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in 1..3){

        }
        var t1 = TableLayout(this)
        var tr1 = TableRow(this)
        var tr2 = TableRow(this)
        var b1 = Button(this)
        var b2 = Button(this)
        var b3 = Button(this)
        var b4 = Button(this)
        var b5 = Button(this)
        var lp: TableLayout.LayoutParams = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        t1.layoutParams = lp
        b1.text = "Column1"
        b2.text = "Column2"
        b3.text = "Column3"
        b4.text = "Column4"
        b5.text = "Column5"
        tr1.addView(b1)
        tr1.addView(b2)
        tr1.addView(b3)
        tr2.addView(b4)
        tr2.addView(b5)
        t1.addView(tr1)
        t1.addView(tr2)
        //setContentView(R.layout.clients)
        setContentView(t1)
    }
}