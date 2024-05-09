package com.example.nfkhusq.Communication

import android.bluetooth.BluetoothSocket
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
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


// ViewModel should be part of your architecture to hold and manage UI-related data in a lifecycle conscious way.
class BluetoothViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<String>>(listOf())
    val messages: LiveData<List<String>> = _messages
    private var connectedSocket: BluetoothSocket? = null
    // Use synchronized if accessing from multiple threads to avoid race conditions
    fun receiveMessage(receivedMessages: List<String>) {
        val currentList = _messages.value ?: emptyList()
        _messages.postValue(currentList + receivedMessages)
    }
    fun sendData(data: String){
        CoroutineScope(Dispatchers.IO).launch{
            connectedSocket?.outputStream?.let {
                try {
                    it.write(data.toByteArray())
                    it.flush()
                    Timber.d("Data sent: $data")
                }catch (e:IOException){
                    Timber.e("Error when sending data", e)
                }
            }
        }
    }
    // Method to receive data from Bluetooth
    fun receiveData() {
        // Example to add received data
        // In reality, you would use Bluetooth API callbacks to update this
        CoroutineScope(Dispatchers.IO).launch {
            val receivedData = listenToBluetoothDevice() // This method needs to be implemented based on your Bluetooth setup
            _messages.postValue(receivedData)
        }
    }

    private fun listenToBluetoothDevice(): List<String> {
        try {
            val inputStream = connectedSocket?.inputStream
            val buffer = ByteArray(1024)
            var bytes: Int
            while (true){
                bytes = inputStream?.read(buffer) ?: -1
                if (bytes == -1) break

                val readMessage = String(buffer, 0, bytes)
                receiveMessage(listOf(readMessage))
            }
        }catch (e: IOException){
            Timber.e("Error when reading from Bluetooth", e)
        }
        // This method should interact with the Bluetooth API to receive data
        // Return the data as a list of strings
        return listOf("Sample Data 1", "Sample Data 2") // Placeholder for actual Bluetooth data
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
