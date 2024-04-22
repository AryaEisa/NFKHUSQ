package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(deviceItem: BluetoothDeviceItem, context: Context,bluetoothAdapter: BluetoothAdapter) {
    /*
    Card: A Compose UI element that provides an elevated card-like appearance.
    It's commonly used to distinguish elements visually from their background.
Modifier.fillMaxWidth(): This modifier ensures the card fills the maximum available width of its parent container.
Modifier.padding(vertical = 8.dp): Applies vertical padding inside the card to increase the spacing
above and below the content for better touch targets and visual separation.
     */
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Increased vertical padding for better tap targets and spacing
            .clickable(onClick = { connectToDevice(deviceItem.device, context, bluetoothAdapter) })
            .alpha(0.7f),
    ) {
        /*
        Row: A layout composable that places its children in a horizontal sequence.
        Here, it's used to align text elements side by side (if there were multiple columns or additional icons,
        they would be laid out here).
        Modifier.padding(16.dp): Applies padding around the content inside the row for spacing from the card edges.
        Modifier.alpha(0.7f): Sets the opacity of the entire row to 70%, making the text and other contents slightly
        transparent for a subdued look.
        Modifier.fillMaxWidth(): Ensures the row uses the full width available within the card.
        verticalAlignment = Alignment.CenterVertically:
            Aligns the children of the row vertically in the center.
         */
        Row(
            modifier = Modifier
                .padding(16.dp)
                .alpha(0.7f) // Set a higher opacity for better readability while keeping the faded look
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*
            Column: A layout composable that places its children vertically.
            In this case, it's used to stack the device name on top of the device address.
            Modifier.weight(1f): In a row, this makes the column take
            up all available space that isn't used by other elements.
            Text: Composables that display text. The first Text shows the device's name,
            and the second shows its address.
            text = deviceItem.device.name ?: "Unnamed Device": Displays the name of the device.
            If the name is null, it defaults to "Unnamed Device".
            fontWeight, fontSize, color: Styling properties to customize the appearance of the text.
            fontWeight = FontWeight.Bold makes the text bold.
            style = MaterialTheme.typography.bodySmall: Applies a predefined text style,
            making it smaller than the default.
            color = MaterialTheme.colorScheme.onSurfaceVariant:
                Uses a color defined in the theme that's designed to contrast less with the background than the primary text color.
            modifier = Modifier.alpha(0.8f): Makes the address text slightly more opaque than the rest of the row.
             */
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
            Button(
                onClick = { connectToDevice(deviceItem.device, context, bluetoothAdapter) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text("Connect}")
            }
        }
    }
}

/*
This is a helper function used to start the discovery of Bluetooth devices:
Checks and Starts Discovery: It first checks if the Bluetooth adapter is not
null and if the discovery process isn't already running (isDiscovering).
If these conditions are met, it starts the Bluetooth discovery.
 */
@SuppressLint("MissingPermission")
fun startDiscovery(bluetoothAdapter: BluetoothAdapter?, isDiscovering: MutableState<Boolean>) {
    if (bluetoothAdapter != null && !isDiscovering.value) {
        isDiscovering.value = bluetoothAdapter.startDiscovery()
    }
}