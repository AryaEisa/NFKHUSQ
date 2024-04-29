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


