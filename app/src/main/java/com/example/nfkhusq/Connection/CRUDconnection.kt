package com.example.nfkhusq.Connection

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import android.widget.Toast
import timber.log.Timber


val connectedDeviceList = mutableListOf<BluetoothDevice>()

// Function to add a connected device
fun addConnectedDevice(device: BluetoothDevice) {
    // Ensuring thread safety with synchronized block if needed
    synchronized(connectedDeviceList) {
        if (connectedDeviceList.any { it.address == device.address }) {
            Timber.d("Device with address " + device.address + " already exists.")
        } else {
            connectedDeviceList.add(device)
            Timber.d("Device added: " + device.address)
        }
    }
}




// Function to remove a disconnected device
fun removeDisconnectedDevice(device: BluetoothDevice) {
    connectedDeviceList.remove(device)
}

// Function to get the list of connected devices
fun getConnectedDevices(): List<BluetoothDevice> {
    return connectedDeviceList.toList()
}