package com.example.nfkhusq.Communication

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.Charset
import java.time.Instant
import java.util.UUID


// Assuming MessageDetail is defined as:
data class MessageDetail(val content: String, val type: String)
// ViewModel should be part of your architecture to hold and manage UI-related data in a lifecycle conscious way.
class BluetoothViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<MessageDetail>>(listOf())
    val messages: LiveData<List<MessageDetail>> = _messages
    var connectedSocket: BluetoothSocket? = null
    // Use synchronized if accessing from multiple threads to avoid race conditions
    // Assume you have a BluetoothSocket already connected
    private val _deviceStatus = MutableLiveData<String>()
    val deviceStatus: LiveData<String> = _deviceStatus

    private fun startPollingDeviceData() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) { // Continues until the ViewModel is cleared
                val status = getDeviceStatusFromDevice() // Implement this method to talk to your device
                _deviceStatus.postValue(status.toString())
                delay(1000) // Poll every second
            }
        }
    }
    // Method to start monitoring the device
    fun startMonitoring() {
        viewModelScope.launch {
            try {
                getDeviceStatusFromDevice()
            } catch (e: IOException) {
                _deviceStatus.postValue("Error: ${e.message}")
            }
        }
    }

    fun stopMonitoring() {
        // Cancelling all children of the ViewModelScope will stop the polling
        viewModelScope.coroutineContext.cancelChildren()
    }
    private suspend fun getDeviceStatusFromDevice() {
        connectedSocket?.inputStream?.let { inputStream ->
            val buffer = ByteArray(1024) // Adjust buffer size as needed
            while (isActive) { // Continues until the ViewModel is cleared or stopped
                val bytesRead = inputStream.read(buffer)
                if (bytesRead != -1) {
                    val readString = String(buffer, 0, bytesRead)
                    _deviceStatus.postValue(readString) // Update live data with the received string
                } else {
                    break // End of stream reached
                }
            }
        } ?: run {
            _deviceStatus.postValue("Bluetooth Socket is not connected")
        }
    }
    fun receiveMessage(receivedMessages: List<MessageDetail>) {
        val currentList = _messages.value ?: emptyList()
        _messages.postValue(currentList + receivedMessages)
    }
    fun sendData(socket: BluetoothSocket?, data: String) {
        socket?.outputStream?.let {
            try {
                it.write(data.toByteArray())
                it.flush() // Make sure the data is sent immediately.
            } catch (e: IOException) {
                // Handle exceptions
            }
        }
    }
    // Method to receive data from Bluetooth
    // This function processes and receives data correctly
    fun receiveData(socket: BluetoothSocket?): String? {
        return try {
            socket?.inputStream?.let { inputStream ->
                // Use a fixed-size buffer to read data
                val buffer = ByteArray(1024)
                val bytesRead = inputStream.read(buffer)
                if (bytesRead != -1) {
                    String(buffer, 0, bytesRead, Charset.defaultCharset())  // Properly encode only the bytes read
                } else {
                    null  // End of stream reached
                }
            }
        } catch (e: IOException) {
            Timber.e("Error receiving Bluetooth data: ${e.message}")
            null  // Return null on error
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


    private fun listenToBluetoothDevice(): List<String> {
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

    // Helper functions to interpret Bluetooth API constants into human-readable strings
    fun getBondState(bondState: Int): String {
        return when (bondState) {
            BluetoothDevice.BOND_BONDED -> "Bonded"
            BluetoothDevice.BOND_NONE -> "Not Bonded"
            else -> "Unknown"
        }
    }

    fun getDeviceType(deviceType: Int): String {
        return when (deviceType) {
            BluetoothDevice.DEVICE_TYPE_CLASSIC -> "Classic Bluetooth"
            BluetoothDevice.DEVICE_TYPE_LE -> "Bluetooth Low Energy"
            BluetoothDevice.DEVICE_TYPE_DUAL -> "Support both BLE & LE"
            else -> "Unknown"
        }
    }

    fun getDeviceClass(deviceClass: BluetoothClass): String {
        return when (deviceClass.majorDeviceClass) {
            BluetoothClass.Device.Major.COMPUTER -> "Computer"
            BluetoothClass.Device.Major.PHONE -> "Phone"
            BluetoothClass.Device.Major.WEARABLE -> "Wearable"
            BluetoothClass.Device.Major.PERIPHERAL -> "Peripheral"
            BluetoothClass.Device.Major.IMAGING -> "Imaging"
            BluetoothClass.Device.Major.MISC -> "Misc"
            BluetoothClass.Device.Major.HEALTH -> "Health"
            BluetoothClass.Device.Major.NETWORKING -> "Networking"
            BluetoothClass.Device.Major.TOY -> "Toy"
            BluetoothClass.Device.Major.AUDIO_VIDEO -> "Audio_Video"
            BluetoothClass.Device.Major.UNCATEGORIZED -> "Uncategorized"
            BluetoothClass.Device.WEARABLE_WRIST_WATCH -> "WEARABLE_WRIST_WATCH"
            BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER -> "AUDIO_VIDEO_CAMCORDER"
            BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> "AUDIO_VIDEO_CAR_AUDIO"
            BluetoothClass.Device.WEARABLE_PAGER -> "WEARABLE_PAGER"
            BluetoothClass.Device.WEARABLE_JACKET -> "WEARABLE_JACKET"
            BluetoothClass.Device.WEARABLE_HELMET -> "WEARABLE_HELMET"
            BluetoothClass.Device.WEARABLE_GLASSES -> "WEARABLE_GLASSES"
            BluetoothClass.Device.TOY_VEHICLE -> "TOY_VEHICLE"
            BluetoothClass.Device.TOY_ROBOT -> "TOY_ROBOT"
            BluetoothClass.Device.TOY_GAME -> "TOY_GAME"
            BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE -> "TOY_DOLL_ACTION_FIGURE"
            BluetoothClass.Device.TOY_CONTROLLER -> "TOY_CONTROLLER"
            BluetoothClass.Device.PHONE_SMART -> "PHONE_SMART"
            BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY -> "PHONE_MODEM_OR_GATEWAY"
            BluetoothClass.Device.PHONE_ISDN -> "PHONE_ISDN"
            BluetoothClass.Device.PHONE_CORDLESS -> "PHONE_CORDLESS"
            BluetoothClass.Device.PHONE_CELLULAR -> "PHONE_CELLULAR"
            BluetoothClass.Device.PERIPHERAL_POINTING -> "PERIPHERAL_POINTING"
            BluetoothClass.Device.PERIPHERAL_NON_KEYBOARD_NON_POINTING -> "PERIPHERAL_NON_KEYBOARD_NON_POINTING"
            BluetoothClass.Device.PERIPHERAL_KEYBOARD_POINTING -> "PERIPHERAL_KEYBOARD_POINTING"
            BluetoothClass.Device.PERIPHERAL_KEYBOARD -> "PERIPHERAL_KEYBOARD"
            BluetoothClass.Device.HEALTH_WEIGHING -> "HEALTH_WEIGHING"
            BluetoothClass.Device.HEALTH_THERMOMETER -> "HEALTH_THERMOMETER"
            BluetoothClass.Device.HEALTH_PULSE_RATE -> "HEALTH_PULSE_RATE"
            BluetoothClass.Device.HEALTH_PULSE_OXIMETER -> "HEALTH_PULSE_OXIMETER"
            BluetoothClass.Device.HEALTH_GLUCOSE -> "HEALTH_GLUCOSE"


            else -> "Other"
        }
    }

    data class BluetoothDeviceItem(
        val device: BluetoothDevice,
        var lastSeen: Instant,
        var supportedUuids: List<UUID> = listOf()
    )


    fun clearMessages() {
        _messages.value = emptyList()
    }
}


