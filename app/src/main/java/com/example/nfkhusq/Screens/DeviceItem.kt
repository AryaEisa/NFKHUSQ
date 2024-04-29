package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfkhusq.Connection.BluetoothDeviceItem
import com.example.nfkhusq.Connection.connectToDevice


@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(deviceItem: BluetoothDeviceItem, context: Context, bluetoothAdapter: BluetoothAdapter) {
    var isConnected by remember { mutableStateOf(false) }
    var isConnecting by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Increased vertical padding for better tap targets and spacing
            .clickable(onClick = {
                isConnecting = true
                connectToDevice(deviceItem.device, context, bluetoothAdapter) { _, _ ->
                    isConnecting = false
                }
            })
            .alpha(0.7f),
    )  {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .alpha(0.7f) // Set a higher opacity for better readability while keeping the faded look
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = deviceItem.device.name ?: "Unnamed Device",
                    fontWeight = FontWeight.Bold, // Make it bold to stand out more
                    fontSize = 18.sp, // Slightly larger for better visibility
                    color = MaterialTheme.colorScheme.onSurface, // Use theme color for text
                    modifier = Modifier.padding(bottom = 4.dp) // Add padding for separation from the address
                )
                Text(
                    text = deviceItem.device.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // A variant for less emphasis
                    modifier = Modifier.alpha(0.8f) // Slightly higher opacity for the address
                )
            }
            if (isConnecting) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Button(
                    onClick = {
                        isConnecting = true
                        connectToDevice(deviceItem.device, context, bluetoothAdapter) { isConnected, _ ->
                            isConnecting = false
                        }
                    }
                    ,  colors = ButtonDefaults.buttonColors(
                        contentColor   = if (isConnecting){Color.Green} else {
                            Color.Red})


                ) {
                    Text("Connect")
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun startDiscovery(bluetoothAdapter: BluetoothAdapter?, isDiscovering: MutableState<Boolean>) {
    if (bluetoothAdapter != null && !isDiscovering.value) {
        isDiscovering.value = bluetoothAdapter.startDiscovery()
    }
}