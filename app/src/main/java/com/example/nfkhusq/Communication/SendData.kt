package com.example.nfkhusq.Communication

import android.bluetooth.BluetoothSocket
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.io.OutputStream

fun SendData(outputStream: OutputStream, data: String){
    try {
        outputStream.write(data.toByteArray())
        outputStream.flush()
        Timber.d("Data send: $data")
    }catch (e: IOException){
        Timber.e("Error when sending data", e)
    }
}

// Assuming MessageDetail is defined as:
data class MessageDetail(val content: String, val type: String)
// ViewModel should be part of your architecture to hold and manage UI-related data in a lifecycle conscious way.
class BluetoothViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<MessageDetail>>(listOf())
    val messages: LiveData<List<MessageDetail>> = _messages
    private var connectedSocket: BluetoothSocket? = null
    // Use synchronized if accessing from multiple threads to avoid race conditions
    fun receiveMessage(receivedMessages: List<MessageDetail>) {
        val currentList = _messages.value ?: emptyList()
        _messages.postValue(currentList + receivedMessages)
    }
    fun sendData(data: String){
        viewModelScope.launch(Dispatchers.IO){
            connectedSocket?.outputStream?.let {
                try {
                    it.write(data.toByteArray())
                    it.flush()
                    Timber.d("Data sent: $data")
                } catch (e: IOException) {
                    Timber.e("Error when sending data", e)
                }
            }
        }
    }
    // Method to receive data from Bluetooth
    // This function processes and receives data correctly
    fun receiveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val receivedMessages = listenToBluetoothDevice()
                if (receivedMessages.isNotEmpty()) {
                    receiveMessage(receivedMessages.map { MessageDetail(it, determineDataType(it.toByteArray(), it.length)) })
                }
            } catch (e: IOException) {
                Timber.e("Error when reading from Bluetooth", e)
            }
        }
    }

    private fun processReceivedData(buffer: ByteArray, byteCount: Int): MessageDetail {
        val content = String(buffer, 0, byteCount)
        val type = determineDataType(buffer, byteCount)
        return MessageDetail(content, type)
    }

    private fun determineDataType(buffer: ByteArray, byteCount: Int): String {
        if (buffer.sliceArray(0 until byteCount).all { it in 32..126 || it.toInt() == 10 || it.toInt() == 13 }) {
            return "Text"
        }
        return "Binary"
    }


    fun listenToBluetoothDevice(): List<String> {
        val messages = mutableListOf<String>()
        try {
            val inputStream = connectedSocket?.inputStream
            val buffer = ByteArray(1024)
            var bytes: Int
            while (true) {
                bytes = inputStream?.read(buffer) ?: -1
                if (bytes == -1) {
                    break  // Properly handle end of stream
                }
                val readMessage = String(buffer, 0, bytes)  // Safely create a string from the valid bytes
                messages.add(readMessage)
                buffer.fill(0)  // Clear the buffer after processing to avoid residual data contamination
            }
        } catch (e: IOException) {
            Timber.e("Error when reading from Bluetooth", e)
        }
        return messages
    }

    private fun getConnectedSocket(): BluetoothSocket? {
        // Implement your logic to obtain the connected BluetoothSocket here
        // This could involve establishing a Bluetooth connection with a device and obtaining the socket
        // Return the connected BluetoothSocket or null if the connection is not established
        return null
    }
    fun clearMessages() {
        _messages.value = emptyList()
    }
}


