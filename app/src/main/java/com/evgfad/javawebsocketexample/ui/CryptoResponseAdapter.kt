package com.evgfad.javawebsocketexample.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.evgfad.javawebsocketexample.R
import com.evgfad.javawebsocketexample.ui.CryptoResponseAdapter.*
import com.evgfad.javawebsocketexample.databinding.ItemCryptoBinding
import com.evgfad.javawebsocketexample.model.crypto_response.OrderBook
import java.math.RoundingMode
import java.text.DecimalFormat

class CryptoResponseAdapter() : RecyclerView.Adapter<CryptoViewHolder>() {

    private val cryptoMap = HashMap<String, OrderBook>()
    private var actualCryptoList: MutableList<Pair<String, OrderBook>> = mutableListOf()

    fun myRVAdapter(mData: HashMap<String, OrderBook>) {
        cryptoMap.putAll(mData)
        val newList = mutableListOf<Pair<String, OrderBook>>()
        cryptoMap.forEach { (s, s2) ->
            newList.add(Pair(s, s2))
        }
        swap(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crypto, parent, false)
        return CryptoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(actualCryptoList[position])
    }

    override fun getItemCount(): Int {
        return actualCryptoList.size
    }

    inner class CryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemCryptoBinding = ItemCryptoBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(crypto: Pair<String, OrderBook>) {
            with(binding) {
                itemCryptoName.text = "${crypto.first}: "
                itemCryptoPrice.text = "${crypto.second.lastPrice}"

                changePercentColorAndText(itemCryptoPercent, crypto.second.priceChangePercent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changePercentColorAndText(textView: TextView, percent: Double?) {
        if (percent != null) {
            if (percent > 0) {
                textView.text = "+${roundOffDecimal(percent)}"
                textView.setTextColor(Color.GREEN)
            } else {
                textView.text = roundOffDecimal(percent)
                textView.setTextColor(Color.RED)
            }
        }
    }

    private fun roundOffDecimal(number: Double?): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toString()
    }

    private fun swap(newCryptoList: List<Pair<String, OrderBook>>) {
        val diffCallback = ActorDiffCallback(actualCryptoList, newCryptoList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        actualCryptoList.clear()
        actualCryptoList.addAll(newCryptoList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ActorDiffCallback(
        private val oldList: List<Pair<String, OrderBook>>,
        private val newList: List<Pair<String, OrderBook>>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].first == newList[newItemPosition].first
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].second.hashCode() == newList[newItemPosition].second.hashCode()
        }

    }

}