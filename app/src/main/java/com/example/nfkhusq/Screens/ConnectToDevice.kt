package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID


@SuppressLint("MissingPermission")
fun connectToDevice(device: BluetoothDevice, context: Context, bluetoothAdapter: BluetoothAdapter) {
    CoroutineScope(Dispatchers.IO).launch {
        var socket: BluetoothSocket? = null
        try {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")  // Standard SerialPortService ID
            socket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothAdapter.cancelDiscovery()  // Cancel discovery as it's heavy on resources
            socket.connect()  // Connect which can block, thus done in the background

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to connect: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } finally {
            try {
                socket?.close()  // Close the socket to release resources
            } catch (e: IOException) {
                e.printStackTrace()  // Log the error if unable to close the socket
            }
        }
    }
}




