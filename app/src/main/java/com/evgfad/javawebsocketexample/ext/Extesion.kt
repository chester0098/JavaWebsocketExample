package com.evgfad.javawebsocketexample.ext

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.roundOffDecimal(number: Double?): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toString()
}