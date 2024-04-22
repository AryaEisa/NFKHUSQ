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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun BluetoothLeScanner(navController: NavController) {
    val context = LocalContext.current
    //bluetoothDevices: A list to hold the Bluetooth devices that are discovered
    val bluetoothDevices = remember { mutableStateListOf<BluetoothDevice>() }
    //bluetoothAdapter: This is used to interact with the Bluetooth hardware on the Android device.
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    //isDiscovering: A state to keep track of whether the app is currently discovering devices.
    val isDiscovering = remember { mutableStateOf(false) }
/*
A permission launcher is set up to request the BLUETOOTH_CONNECT permission if
it's not already granted. This is essential for accessing Bluetooth capabilities
in Android 12 and above.

 */
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                startDiscovery(bluetoothAdapter, isDiscovering)
            } else {
                // Handle the case where permission is not granted
            }
        }
    )
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
This is a component that listens for Bluetooth devices being found nearby.
If a device is found and it has a name, it adds the device to the list bluetoothDevices.
 */
    val bluetoothReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        device?.let {
                            if (device.name != null) {
                                bluetoothDevices.add(device)
                            }
                        }
                    }
                }
            }
        }
    }
/*
This ensures that the Bluetooth receiver is registered when the UI is visible and
unregistered when the UI is no longer in use, helping to manage resources efficiently
and avoid memory leaks.
 */
    DisposableEffect(Unit) {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(bluetoothReceiver, filter)
        onDispose {
            context.unregisterReceiver(bluetoothReceiver)
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
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(bluetoothDevices) { device ->
                    DeviceItem(device)
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
@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(device: BluetoothDevice) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(device.name ?: "Unnamed Device", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Text(device.address, style = MaterialTheme.typography.bodySmall)
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
private fun startDiscovery(bluetoothAdapter: BluetoothAdapter?, isDiscovering: MutableState<Boolean>) {
    if (bluetoothAdapter != null && !isDiscovering.value) {
        isDiscovering.value = bluetoothAdapter.startDiscovery()
    }
}
/*
Summary::
In essence, your code sets up a user interface to scan for and display Bluetooth
devices using Android's Bluetooth API, with permission handling built-in. It uses
modern Android development practices with Jetpack Compose to create a responsive
and efficient UI.
 */
@Preview
@Composable
fun BluetoothLeScannerPreview() {
    BluetoothLeScanner(navController = rememberNavController())
}