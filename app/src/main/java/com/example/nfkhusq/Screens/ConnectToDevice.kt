package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
fun connectToDevice(
    device: BluetoothDevice,
    context: Context,
    bluetoothAdapter: BluetoothAdapter?,
    onConnectionComplete: (Boolean) -> Unit
) {
    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
        Log.e("BluetoothConnect", "Bluetooth is disabled or not available.")
        onConnectionComplete(false)
        return
    }

    val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")  // Standard SPP UUID for most Bluetooth modules
    CoroutineScope(Dispatchers.IO).launch {
        var socket: BluetoothSocket? = null
        try {
            socket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothAdapter.cancelDiscovery()
            socket.connect()
            Log.d("BluetoothConnect", "Successfully connected to ${device.address}")
            withContext(Dispatchers.Main) {
                onConnectionComplete(true)
            }
        } catch (e: IOException) {
            Log.e("BluetoothConnect", "Failed to connect to ${device.address}", e)
            socket?.closeQuietly()
            withContext(Dispatchers.Main) {
                onConnectionComplete(false)
            }
        }
    }
}

fun BluetoothSocket?.closeQuietly() {
    try {
        this?.close()
    } catch (e: IOException) {
        Log.e("BluetoothConnect", "Could not close the client socket", e)
    }
}



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





