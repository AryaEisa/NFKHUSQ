package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nfkhusq.Connection.getBondState
import com.example.nfkhusq.Connection.getDeviceClass
import com.example.nfkhusq.Connection.getDeviceType

@SuppressLint("MissingPermission")
@Composable
fun DeviceDetailView(device: BluetoothDevice) {
    DeviceDetails(device = device)
}