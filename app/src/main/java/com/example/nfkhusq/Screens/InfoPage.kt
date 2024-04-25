package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nfkhusq.Connection.getConnectedDevices


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun InfoPage() {
    val connectedDevices = remember { getConnectedDevices() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top, // Align header to top
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // Add padding for content
        ) {
            Text(
                text = "Connected Devices",
                style = MaterialTheme.typography.bodySmall, // Use h6 for section header
                modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for title
                ,color = Color.White
            )
            if (connectedDevices.isEmpty()) {
                // Display a placeholder message for no devices
                Text(
                    text = "No connected devices found.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.run { padding(horizontal = 16.dp) }, color = Color.White
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






