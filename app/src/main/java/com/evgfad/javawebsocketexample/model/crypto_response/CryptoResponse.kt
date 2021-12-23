package com.evgfad.javawebsocketexample.model.crypto_response

import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("ch") val ch: String,
    @SerializedName("data") val `data`: HashMap<String, OrderBook>
)