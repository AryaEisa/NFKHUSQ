package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfkhusq.Communication.BluetoothViewModel



@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecieveDataView(bluetoothViewModel: BluetoothViewModel = viewModel(), navController: NavController, device: BluetoothDevice) {
    val messages by bluetoothViewModel.messages.observeAsState(initial = emptyList())
    val testing = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bluetooth Data Receiver") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    if (testing.value) {
                        item {
                            ReceiveView(bluetoothViewModel = bluetoothViewModel, navController = navController, device = device)
                        }
                    }
                }

                Button(
                    onClick = {
                        testing.value = true  // Update the state to show the device details
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Receive Data")
                }

                Button(
                    onClick = {
                        bluetoothViewModel.clearMessages()
                        testing.value = false  // Optionally reset the testing state
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Messages")
                }
            }
        }
    )
}

@SuppressLint("MissingPermission")
@Composable
fun ReceiveView(bluetoothViewModel: BluetoothViewModel, navController: NavController, device: BluetoothDevice) {
    val deviceStatus by bluetoothViewModel.deviceStatus.observeAsState("No Data")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Device Name: ${device.name}", style = MaterialTheme.typography.bodyMedium)
        Text("Device Address: ${device.address}", style = MaterialTheme.typography.bodyMedium)
        Text("Bond State: ${bluetoothViewModel.getBondState(device.bondState)}", style = MaterialTheme.typography.bodyMedium)
        Text("Device Type: ${bluetoothViewModel.getDeviceType(device.type)}", style = MaterialTheme.typography.bodyMedium)
        Text("Device Class: ${bluetoothViewModel.getDeviceClass(device.bluetoothClass)}", style = MaterialTheme.typography.bodyMedium)
        Text("Current Status: $deviceStatus", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Connected Socket: ${bluetoothViewModel.connectedSocket.toString()}")

        // Control buttons to start/stop monitoring
        Row {
            Button(onClick = { bluetoothViewModel.startMonitoring() }) {
                Text("Start Monitoring")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { bluetoothViewModel.stopMonitoring() }) {
                Text("Stop Monitoring")
            }
        }
    }
}
