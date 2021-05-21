package com.example.clients.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Order (
    var amount: Long,
    var clientID: String,
    var date: Timestamp,
    var name: String,
    var price: Long
)