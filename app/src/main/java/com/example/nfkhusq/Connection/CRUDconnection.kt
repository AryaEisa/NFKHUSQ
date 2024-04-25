package com.example.nfkhusq.Connection

import android.bluetooth.BluetoothDevice


// Define a list to store connected devices
val connectedDeviceList = mutableListOf<BluetoothDevice>()

// Function to add a connected device
fun addConnectedDevice(device: BluetoothDevice) {
    connectedDeviceList.add(device)
}

// Function to remove a disconnected device
fun removeDisconnectedDevice(device: BluetoothDevice) {
    connectedDeviceList.remove(device)
}

// Function to get the list of connected devices
fun getConnectedDevices(): List<BluetoothDevice> {
    return connectedDeviceList.toList()
}