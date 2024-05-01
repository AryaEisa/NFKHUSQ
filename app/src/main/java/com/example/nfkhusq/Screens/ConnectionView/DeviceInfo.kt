@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

package com.example.nfkhusq.Screens.ConnectionView

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.Connection.getBondState
import com.example.nfkhusq.Connection.getDeviceClass
import com.example.nfkhusq.Connection.getDeviceType
import com.example.nfkhusq.R


@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("MissingPermission")
@Composable
fun DeviceInfo(navController: NavController, device: BluetoothDevice) {
    val detailsVisible = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
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

        }
    }
}
