package com.example.nfkhusq.Communication

import android.bluetooth.BluetoothSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

fun StartCommunicationThread(socket: BluetoothSocket){
    CoroutineScope(Dispatchers.IO).launch {
        val input = socket.inputStream
        val output = socket.outputStream
        val buffer = ByteArray(1024)
        var numBytea: Int
        try {
            while (true){
                numBytea = input.read(buffer)
                val readMsg = String(buffer, 0, numBytea)
                Timber.d("Received: $readMsg")
            }
        }catch (e: IOException){
            Timber.e("Input stream was disconnected", e)
        } finally {
            try {
                input.close()
                output.close()
                socket.close()
            } catch (e: IOException){
                Timber.e("Error when closing the socket or streams")
            }
        }
    }
}