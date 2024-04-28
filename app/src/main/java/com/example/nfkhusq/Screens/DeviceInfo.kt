@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.Connection.getBondState
import com.example.nfkhusq.Connection.getDeviceClass
import com.example.nfkhusq.Connection.getDeviceType


@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("MissingPermission")
@Composable
fun DeviceInfo(device: BluetoothDevice) {

    Card(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        val navController = rememberNavController()
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
                text = "   Adress:  ${device.address}",
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Subdued color for address
            )
            Text(
                text = "    Bond State: ${getBondState(device.bondState)}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "    Device Type: ${getDeviceType(device.type)}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "    Device Class: ${getDeviceClass(device.bluetoothClass)}",
                style = MaterialTheme.typography.bodySmall
            )


            // Add more information about the device as needed
        }
    }
}
