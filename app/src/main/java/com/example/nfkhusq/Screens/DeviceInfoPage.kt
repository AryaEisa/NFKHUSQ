package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


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

@Composable
fun InfoPage() {
    val connectedDevices = remember { getConnectedDevices() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            verticalArrangement = Arrangement.Top, // Align header to top
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // Add padding for content
        ) {
            Text(
                text = "Connected Devices",
                style = MaterialTheme.typography.bodySmall, // Use h6 for section header
                modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for title
            )
            if (connectedDevices.isEmpty()) {
                // Display a placeholder message for no devices
                Text(
                    text = "No connected devices found.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.run { padding(horizontal = 16.dp) }
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(connectedDevices) { device ->
                        DeviceInfo(device = device)
                        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between devices
                        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)) // Use a lighter divider
                    }
                }
            }
        }
    }
}



@SuppressLint("MissingPermission")
@Composable
fun DeviceInfo(device: BluetoothDevice) {
    Card(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {

                Text(
                    text = "    Name: ${device.name ?: "Unknown"}",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "    ${device.address}",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Subdued color for address
                )

            // Add more information about the device as needed
        }
    }
}


