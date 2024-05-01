package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("MissingPermission")
@Composable
fun SendDataToDevice(navController: NavController, device: BluetoothDevice) {
    val detailsVisible = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface, // Use theme surface color
        shape = RoundedCornerShape(8.dp) // Rounded corners for better visual integration
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    detailsVisible.value = !detailsVisible.value
                    if (detailsVisible.value) {
                        navController.navigate("deviceDetails/${device.address}")
                    }
                }
                .padding(16.dp) // Increased padding for touch area and visual comfort
        ) {
            Text(
                text = "Name: ${device.name ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium, // Larger text for primary information
                color = MaterialTheme.colorScheme.onSurface // Text color that contrasts with the surface
            )
            Row {
                CustomButtons()
            }
        }
    }
}
