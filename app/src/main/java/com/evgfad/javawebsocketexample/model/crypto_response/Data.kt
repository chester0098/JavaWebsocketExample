package com.evgfad.javawebsocketexample.model.crypto_response

import java.util.*

data class Data(
    val orderBook: Dictionary<String, OrderBook>
)