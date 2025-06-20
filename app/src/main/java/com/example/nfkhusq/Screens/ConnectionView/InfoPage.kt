@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nfkhusq.Screens.ConnectionView

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nfkhusq.Connection.getConnectedDevices
import com.example.nfkhusq.R


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun InfoPage(navController: NavController) {
    val context = LocalContext.current
    val connectedDevices = remember { getConnectedDevices() }
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("", color = Color.White) },
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
            Image(
                painter = painterResource(id = R.drawable.husq3),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp).padding(paddingValues)
            ) {
                Text(
                    text = "Connected Devices",
                    style = MaterialTheme.typography.bodyMedium, // Use larger typography for the title
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.White // Ensure the color contrasts well with the background
                )
                if (connectedDevices.isEmpty()) {
                    Text(
                        text = "No connected devices found.",
                        style = MaterialTheme.typography.bodyMedium, // More prominent style for important messages
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                        color = Color.White // Use an error color to highlight no devices
                    )
                } else {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        items(connectedDevices) { device ->
                            DeviceInfo(navController = navController, device = device)
                            Spacer(modifier = Modifier.height(8.dp)) // Maintain visual spacing
                            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                        }
                    }
                }
            }
        }
    }
}






