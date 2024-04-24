@file:Suppress("DEPRECATION")

package com.example.nfkhusq.Screens

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.nfkhusq.R
import java.time.Instant

data class BluetoothDeviceItem(
    val device: BluetoothDevice,
    var lastSeen: Instant
)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission", "InlinedApi")
@Composable
fun BluetoothLeScanner(navController: NavController) {
    val context = LocalContext.current
    //bluetoothDevices: A list to hold the Bluetooth devices that are discovered
    val bluetoothDevices = remember { mutableStateListOf<BluetoothDeviceItem>() }
    //bluetoothAdapter: This is used to interact with the Bluetooth hardware on the Android device.
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    //isDiscovering: A state to keep track of whether the app is currently discovering devices.
    val isDiscovering = remember { mutableStateOf(false) }
    remember { mutableListOf<BluetoothDevice>() }
    /*
    A permission launcher is set up to request the BLUETOOTH_CONNECT permission if
    it's not already granted. This is essential for accessing Bluetooth capabilities
    in Android 12 and above.

     */

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                startDiscovery(bluetoothAdapter, isDiscovering)
            } else {
                Toast.makeText(context, "Bluetooth permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )


    /*
    This ensures that the Bluetooth receiver is registered when the UI is visible and
    unregistered when the UI is no longer in use, helping to manage resources efficiently
    and avoid memory leaks.
     */
    DisposableEffect(context) {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        /*
This is a component that listens for Bluetooth devices being found nearby.
If a device is found and it has a name, it adds the device to the list bluetoothDevices.
 */

        val bluetoothReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.let { device ->
                    if (device.name != null) {
                        val existingDevice = bluetoothDevices.firstOrNull { it.device.address == device.address }
                        if (existingDevice != null) {
                            existingDevice.lastSeen = Instant.now()
                        } else {
                            bluetoothDevices.add(BluetoothDeviceItem(device, Instant.now()))
                        }
                    }
                }
            }
        }
        context.registerReceiver(bluetoothReceiver, filter)
        onDispose {
            context.unregisterReceiver(bluetoothReceiver)
        }
    }
    /*
 LaunchedEffect: This block checks if the necessary Bluetooth permission is granted.
 If not, it launches a request for this permission. If the permission is already granted,
 it starts the Bluetooth discovery process.
  */
    LaunchedEffect(key1 = Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            startDiscovery(bluetoothAdapter, isDiscovering)
        }
    }

    /*
    A scaffold structure with a top app bar displaying the title "Bluetooth Scanner".
    Inside the scaffold, there's a column layout that lists all discovered Bluetooth
    devices using a LazyColumn, which is efficient for displaying lists of data.
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth Scanner") }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Image(
                painter = painterResource(id = R.drawable.husq3),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .alpha(0.3f)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Discovered Bluetooth Devices:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Button(
                    onClick = { navController.navigate("InfoPage") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding for better spacing
                        .background(color = MaterialTheme.colorScheme.primary), // Use primary color
                    shape = MaterialTheme.shapes.medium // Use Material Design shape
                ) {
                    Row( // Arrange content horizontally
                        horizontalArrangement = Arrangement.Start, // Align content to the left
                        modifier = Modifier.fillMaxWidth() // Stretch row to fill button width
                    ) {
                        Icon(
                            Icons.Filled.Bluetooth,
                            contentDescription = "Connected Devices",
                            tint = Color.White // Use white tint for better visibility on primary background
                        )
                        Spacer(Modifier.width(8.dp)) // Maintain spacing between icon and text
                        Text(
                            text = "Connected Devices",
                            color = Color.White, // Use white text color for contrast
                            fontSize = 16.sp, // Adjust font size as needed (consider MaterialTheme.typography.body1)
                            fontWeight = FontWeight.Medium // Use medium weight for emphasis (optional)
                        )
                    }
                }

                /*
                Button(
                    onClick = { navController.navigate("InfoPage") },
                    modifier = Modifier.fillMaxWidth(),

                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Info page")
                    Spacer(Modifier.width(8.dp))
                    Text("My devices")
                }
                */
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(bluetoothDevices) { deviceItem ->
                        DeviceItem(deviceItem, context, bluetoothAdapter!!)
                    }
                }
            }
        }
    }
}
/*
This function is responsible for how each Bluetooth device is displayed:

Card and Row Layout: Each device is shown in a card format, making it
visually distinct. Inside the card, the device's name and address are displayed in a row format.
 */

/*
Summary::
In essence, your code sets up a user interface to scan for and display Bluetooth
devices using Android's Bluetooth API, with permission handling built-in. It uses
modern Android development practices with Jetpack Compose to create a responsive
and efficient UI.
 */
