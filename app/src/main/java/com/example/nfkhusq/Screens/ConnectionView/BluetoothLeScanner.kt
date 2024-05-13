@file:Suppress("DEPRECATION")

package com.example.nfkhusq.Screens.ConnectionView

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.nfkhusq.Communication.BluetoothViewModel
import com.example.nfkhusq.R
import java.time.Instant


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission", "InlinedApi")
@Composable
fun BluetoothLeScanner(navController: NavController, viewModel: BluetoothViewModel) {
    val context = LocalContext.current
    //bluetoothDevices: A list to hold the Bluetooth devices that are discovered
    val bluetoothDevices = remember { mutableStateListOf<BluetoothViewModel.BluetoothDeviceItem>() }
    //bluetoothAdapter: This is used to interact with the Bluetooth hardware on the Android device.
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    //isDiscovering: A state to keep track of whether the app is currently discovering devices.
    val isDiscovering = remember { mutableStateOf(false) }
    remember { mutableListOf<BluetoothDevice>() }
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
    DisposableEffect(context) {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val bluetoothReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.let { device ->
                    if (device.name != null) {
                        val existingDevice = bluetoothDevices.firstOrNull { it.device.address == device.address }
                        if (existingDevice != null) {
                            existingDevice.lastSeen = Instant.now()
                        } else {
                            bluetoothDevices.add(
                                BluetoothViewModel.BluetoothDeviceItem(
                                    device,
                                    Instant.now()
                                )
                            )
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
    LaunchedEffect(key1 = Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            startDiscovery(bluetoothAdapter, isDiscovering)
        }
    }
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Bluetooth Scanner", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
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
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(bluetoothDevices) { deviceItem ->
                        DeviceItem(deviceItem, context, bluetoothAdapter!!, viewModel )
                    }
                }
            }
        }
    }
}

/*
Summary::
In essence, your code sets up a user interface to scan for and display Bluetooth
devices using Android's Bluetooth API, with permission handling built-in. It uses
modern Android development practices with Jetpack Compose to create a responsive
and efficient UI.
 */
