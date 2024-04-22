package com.example.nfkhusq.Bluetooth

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat


@SuppressLint("MissingPermission")
@Composable
fun BluetoothLeScanner() {
    val context = LocalContext.current
    val bluetoothDevices = remember { mutableStateListOf<BluetoothDevice>() }
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    val isDiscovering = remember { mutableStateOf(false) }

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

    LaunchedEffect(key1 = Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            startDiscovery(bluetoothAdapter, isDiscovering)
        }
    }

    val bluetoothReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        device?.let {
                            bluetoothDevices.add(device)
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(bluetoothReceiver, filter)
        onDispose {
            context.unregisterReceiver(bluetoothReceiver)
        }
    }

    Column {
        Text("Discovered Bluetooth Devices:")
        LazyColumn {
            items(bluetoothDevices) { device ->
                Text(device.name ?: "Unnamed Device")
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun startDiscovery(bluetoothAdapter: BluetoothAdapter?, isDiscovering: MutableState<Boolean>) {
    if (bluetoothAdapter != null && !isDiscovering.value) {
        isDiscovering.value = bluetoothAdapter.startDiscovery()
    }
}

@Preview
@Composable
fun BluetoothLeScannerPreview() {
    BluetoothLeScanner()
}