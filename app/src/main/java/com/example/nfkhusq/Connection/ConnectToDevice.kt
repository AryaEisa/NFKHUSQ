package com.example.nfkhusq.Connection
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.widget.Toast
import com.example.nfkhusq.Connection.addConnectedDevice
import com.example.nfkhusq.Connection.removeDisconnectedDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.util.UUID



@SuppressLint("MissingPermission")
fun connectToDevice(
    device: BluetoothDevice,
    context: Context,
    bluetoothAdapter: BluetoothAdapter,
    onConnectionComplete: (Boolean, BluetoothSocket?) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        val classicUuids = listOf(
            "00001101-0000-1000-8000-00805F9B34FB",  // Serial Port Profile (SPP)
            "0000111E-0000-1000-8000-00805F9B34FB",  // Hands-Free Profile (HFP)
            "0000110A-0000-1000-8000-00805F9B34FB",  // Advanced Audio Distribution Profile (A2DP)
            "0000110E-0000-1000-8000-00805F9B34FB",  // Audio/Video Remote Control Profile (AVRCP)
            "00001105-0000-1000-8000-00805F9B34FB"   // Object Push Profile (OPP)
        )
        bluetoothAdapter.cancelDiscovery()

        var socket: BluetoothSocket? = null
        var connected = false

        fun updateConnectionStatus() {
            CoroutineScope(Dispatchers.Main).launch {
                onConnectionComplete(connected, if (connected) socket else null)
                Toast.makeText(context, if (connected) "Connected to ${device.name}" else "Failed to connect to ${device.name}", Toast.LENGTH_SHORT).show()
                if (connected) {
                    addConnectedDevice(device)
                }
            }
        }

        try {
            for (uuidString in classicUuids) {
                if (!isActive) return@launch  // Handle coroutine cancellation
                try {
                    val uuid = UUID.fromString(uuidString)
                    socket = device.createRfcommSocketToServiceRecord(uuid)
                    socket.connect()
                    connected = true
                    break
                } catch (e: IOException) {
                    continue
                }
            }

            if (!connected) {
                device.connectGatt(context, false, object : BluetoothGattCallback() {
                    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                        super.onConnectionStateChange(gatt, status, newState)
                        if (newState == BluetoothProfile.STATE_CONNECTED) {
                            connected = true
                            gatt?.discoverServices()
                            addConnectedDevice(device)
                        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                            gatt?.close()
                            removeDisconnectedDevice(device)
                        }
                        updateConnectionStatus()
                    }
                })
            } else {
                updateConnectionStatus()
            }
        } catch (e: Exception) {
            Timber.e("Exception in connection: ${e.message}")
            updateConnectionStatus()
        }
    }
}




/*
@SuppressLint("MissingPermission")
fun connectToDevice(
    device: BluetoothDevice,
    context: Context,
    bluetoothAdapter: BluetoothAdapter,
    onConnectionComplete: (Boolean, BluetoothSocket?) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        val classicUuids = listOf(
            "00001101-0000-1000-8000-00805F9B34FB",  // Serial Port Profile (SPP)
            "0000111E-0000-1000-8000-00805F9B34FB",  // Hands-Free Profile (HFP)
            "0000110A-0000-1000-8000-00805F9B34FB",  // Advanced Audio Distribution Profile (A2DP)
            "0000110E-0000-1000-8000-00805F9B34FB",  // Audio/Video Remote Control Profile (AVRCP)
            "00001105-0000-1000-8000-00805F9B34FB"   // Object Push Profile (OPP)
        )


        bluetoothAdapter.cancelDiscovery()

        var socket: BluetoothSocket? = null
        var connected = false

        // If classic Bluetooth connection failed, try BLE connection
        if (!connected) {
            val gattCallback = object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                    super.onConnectionStateChange(gatt, status, newState)
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        connected = true
                        gatt?.discoverServices() // Optionally discover services here
                        addConnectedDevice(device)
                        println("${device.name} added into Connected Devices")


                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        connected = false
                        gatt?.close()
                        removeDisconnectedDevice(device)
                    }

                    // Update UI on the main thread
                    CoroutineScope(Dispatchers.Main).launch {
                        onConnectionComplete(connected, null) // Note the null, as there's no BluetoothSocket for BLE
                        Toast.makeText(context, if (connected) "Connected to ${device.name}"  else "Failed to connect to ${device.name}", Toast.LENGTH_SHORT).show()
                        delay(3000)
                        Toast.makeText(context, if(connected) "${device.name} added to Connected Device" else "Please turn on your device for connection", Toast.LENGTH_LONG).show()
                    }
                }
            }

            device.connectGatt(context, false, gattCallback)
        }
        try {
            for (uuidString in classicUuids) {
                try {
                    val uuid = UUID.fromString(uuidString)
                    socket = device.createRfcommSocketToServiceRecord(uuid)
                    socket.connect()
                    connected = true
                    break
                } catch (e: IOException) {
                    println("Failed to connect with UUID " + uuidString + ": " + e.message)
                    Timber.e("Failed to connect with UUID " + uuidString + ": " + e.message)
                }
            }

        } catch (e: Exception) {
            socket?.close()
            socket = null
            Timber.e("Exception in connection: " + e.message)
        }
        finally {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, if (connected) "Connected to ${device.name}" else "Failed to connect to ${device.name}", Toast.LENGTH_SHORT).show()
                onConnectionComplete(connected, socket)
                if (connected){
                    addConnectedDevice(device)
                }
            }
        }
        while (connected) {
            delay(3000)

            try {
                // Attempt to read from the input stream to detect disconnection
                val buffer = ByteArray(1)
                val bytesRead = socket?.inputStream?.read(buffer)

                if (bytesRead == -1) {
                    // Disconnection detected
                    connected = false
                    println("Disconnected from ${device.name}")
                    removeDisconnectedDevice(device)
                    break
                } else {

                    println("Still connected to ${device.name}")
                }
            } catch (e: IOException) {
                // Disconnection detected
                connected = false
                println("Disconnected from ${device.name}: ${e.message}")
                //Toast.makeText(context, "${device.name}: Lost Connection", Toast.LENGTH_SHORT).show()
                removeDisconnectedDevice(device)
                break
            }
        }
    }
}
fun disconnectDevice(socket: BluetoothSocket?) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            socket?.close()
            println("Disconnected from device")
        } catch (e: IOException) {
            Timber.e("Failed to close connection: " + e.message)
        }
    }
}

 */






/*
fun connectToDevice(device: BluetoothDevice, context: Context, bluetoothAdapter: BluetoothAdapter) {
    CoroutineScope(Dispatchers.IO).launch {
        var socket: BluetoothSocket? = null
        try {
            // Standard SerialPortService ID
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            socket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothAdapter.cancelDiscovery() // Cancel discovery as it's heavy on resources
            socket.connect() // Connect which can block, thus done in the background

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Timber.e(e, "Initial connection failed: ${e.message}")
            // Attempt fallback connection
            try {
                socket?.close() // Close the initial socket
                socket = createFallbackSocket(device)
                socket.connect()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Connected to ${device.name} on second try", Toast.LENGTH_SHORT).show()
                }
            } catch (e2: Exception) {
                Timber.e(e2, "Fallback connection failed: ${e2.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to connect on second try: ${e2.message}", Toast.LENGTH_LONG).show()
                }
            }
        } finally {
            try {
                socket?.close() // Close the socket when it's no longer needed
            } catch (e: IOException) {
                Timber.e(e, "Error closing socket: ${e.message}")
            }
        }
    }
}

private fun createFallbackSocket(device: BluetoothDevice): BluetoothSocket {
    return try {
        val method = device.javaClass.getMethod("createRfcommSocket", Int::class.javaPrimitiveType)
        method.invoke(device, 1) as BluetoothSocket
    } catch (e: NoSuchMethodException) {
        Timber.e(e, "Reflection method access error")
        throw RuntimeException("Reflection failed", e)
    } catch (e: IllegalAccessException) {
        Timber.e(e, "Illegal access in reflection method")
        throw RuntimeException("Reflection failed", e)
    } catch (e: InvocationTargetException) {
        Timber.e(e, "Invocation target exception in reflection method")
        throw RuntimeException("Reflection failed", e)
    }
}

 */





