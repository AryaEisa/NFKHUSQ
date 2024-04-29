@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.Connection.getBondState
import com.example.nfkhusq.Connection.getDeviceClass
import com.example.nfkhusq.Connection.getDeviceType


@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("MissingPermission")
@Composable
fun DeviceInfo(navController: NavController, device: BluetoothDevice) {
    val batteryLevel = remember { mutableStateOf("Unknown") }
    val detailsVisible = remember { mutableStateOf(false) }

    LaunchedEffect(device) {
        batteryLevel.value = getBatteryLevel(device)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Name: ${device.name ?: "Unknown"}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .clickable {
                        detailsVisible.value = !detailsVisible.value
                        if (detailsVisible.value) {
                            // Pass the device address as a parameter
                            navController.navigate("deviceDetails/${device.address}")
                        }
                    },
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


@SuppressLint("MissingPermission")
@Composable
fun DeviceDetails(device: BluetoothDevice) {
    Column {
        Text(
            text = "Address: ${device.address}",
            modifier = Modifier.padding(bottom = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = "Bond State: ${getBondState(device.bondState)}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Device Type: ${getDeviceType(device.type)}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Device Class: ${getDeviceClass(device.bluetoothClass)}",
            style = MaterialTheme.typography.bodySmall
        )

    }
}

fun getBatteryLevel(device: BluetoothDevice): String {
    // Placeholder: Fetch the battery level from the device's GATT profile
    return "75%"  // Example value
}