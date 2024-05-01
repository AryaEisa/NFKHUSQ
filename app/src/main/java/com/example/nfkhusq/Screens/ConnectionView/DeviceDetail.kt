@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nfkhusq.Screens.ConnectionView

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nfkhusq.Connection.getBondState
import com.example.nfkhusq.Connection.getDeviceClass
import com.example.nfkhusq.Connection.getDeviceType
import com.example.nfkhusq.R
import com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection.SendReceiveView


@SuppressLint("MissingPermission", "NewApi")
@Composable
fun DeviceDetails(device: BluetoothDevice, navController: NavController) {
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(device.name, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues: PaddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.husq3),
                    contentDescription = "Background Image",
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.2f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp).padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                    DetailItem("Address", device.address)
                    DetailItem("Bond State", getBondState(device.bondState))
                    DetailItem("Device Type", getDeviceType(device.type))
                    DetailItem("Device Class", getDeviceClass(device.bluetoothClass))
                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                    SendReceiveView(navController = navController)
                }
            }
        }
    }
}


