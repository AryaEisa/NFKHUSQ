package com.example.nfkhusq.Screens.ConnectionView

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.nfkhusq.Screens.ConnectionView.DetailItem
import com.example.nfkhusq.Screens.ConnectionView.InfoPage


@SuppressLint("MissingPermission", "NewApi")
@Composable
fun DeviceDetails(device: BluetoothDevice, navController: NavController) {
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
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${device.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                DetailItem("Address", device.address)
                DetailItem("Bond State", getBondState(device.bondState))
                DetailItem("Device Type", getDeviceType(device.type))
                DetailItem("Device Class", getDeviceClass(device.bluetoothClass))
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                InfoPage(navController = navController)
                }
            }
        }
    }


