package com.evgfad.javawebsocketexample.model.crypto_response

import com.google.gson.annotations.SerializedName

data class OrderBook(
    @SerializedName("t") val timestamp: Long?,
    @SerializedName("A") val Sequence: String?,
    @SerializedName("a") val asks: String?,
    @SerializedName("b") val bids: String?,
    @SerializedName("c") val lastPrice: String?,
    @SerializedName("P") val priceChangePercent:Double?
)

/*
@SerializedName("t") val timestamp : Int,
@SerializedName("a") val bestAsk : Double,
@SerializedName("A") val bestAskQuantity : Double,
@SerializedName("b") val bestBid : Double,
@SerializedName("B") val bestBidQuantity : Double,
@SerializedName("c") val lastPrice : Double,
@SerializedName("o") val openPrice : Double,
@SerializedName("h") val highPrice : Double,
@SerializedName("l") val l : Double,
@SerializedName("v") val v : Double,
@SerializedName("q") val q : Double,
@SerializedName("p") val p : Double,
@SerializedName("P") val p : Double,
@SerializedName("L") val l : Int*/
