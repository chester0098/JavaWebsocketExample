package com.evgfad.javawebsocketexample.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.evgfad.javawebsocketexample.R
import com.evgfad.javawebsocketexample.databinding.ActivityMainBinding
import com.evgfad.javawebsocketexample.model.crypro_request.CryptoRequest
import com.evgfad.javawebsocketexample.model.crypto_response.CryptoResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class MainActivity : AppCompatActivity() {

    private lateinit var webSocketClient: WebSocketClient
    private lateinit var gson: Gson
    private lateinit var cryptoResponse: CryptoResponse

    private lateinit var binding: ActivityMainBinding
    private lateinit var cryptoResponseAdapter: CryptoResponseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cryptoResponseAdapter = CryptoResponseAdapter()
        gson = Gson()

        binding.recycler.adapter = cryptoResponseAdapter
    }

    override fun onResume() {
        super.onResume()
        initWebSocket()
    }

    private fun initWebSocket() {
        createWebSocketClient(URI(WEB_SOCKET_URL))
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen: ")
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                cryptoResponse = gson.fromJson(message, CryptoResponse::class.java)

                lifecycleScope.launch(Dispatchers.Main) {
                    if (cryptoResponse.data.isNullOrEmpty())
                        Log.d(TAG, "onMessage: NULL")
                    else
                        cryptoResponseAdapter.myRVAdapter(cryptoResponse.data)
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose: ")
            }

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: $ex")
            }
        }
        webSocketClient.connect()
    }


    private fun subscribe() {
        val cryptoRequest = CryptoRequest()
        webSocketClient.send(
            gson.toJson(cryptoRequest)
        )
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    companion object {
        const val WEB_SOCKET_URL = "wss://api.multiexchange.com/api/3/ws/public"
        const val TAG = "TAG"
    }
}