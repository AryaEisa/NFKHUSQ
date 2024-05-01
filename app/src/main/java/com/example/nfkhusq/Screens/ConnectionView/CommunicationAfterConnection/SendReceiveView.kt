package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nfkhusq.Connection.getConnectedDevices
import com.example.nfkhusq.R


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun SendReceiveView(navController: NavController) {
    val connectedDevices = remember { getConnectedDevices() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Image(
            painter = painterResource(id = R.drawable.husq3),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "All Connected Devices",
                style = MaterialTheme.typography.bodyMedium, // Use larger typography for the title
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White // Ensure the color contrasts well with the background
            )
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(connectedDevices) { device ->
                        SendDataToDevice(navController = navController, device = device)
                        Spacer(modifier = Modifier.height(8.dp)) // Maintain visual spacing
                        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    }
                }

        }
    }
}