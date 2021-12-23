package com.evgfad.javawebsocketexample.model.crypro_request

import com.google.gson.annotations.SerializedName

data class CryptoRequest(
    @SerializedName(value = "params")
    val params: Params = Params(),
    @SerializedName(value = "ch")
    val ch: String = "ticker/1s/batch",
    @SerializedName(value = "id")
    val id: Int = 123,
    @SerializedName(value = "method")
    var method: String = "subscribe"
)