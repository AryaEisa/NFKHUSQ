package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun CustomButtons(device: BluetoothDevice,bluetoothViewModel: BluetoothViewModel = viewModel(), navController: NavController) {

    // Send Data Button
    Button(onClick = { /*TODO*/ }) {
        Icon(Icons.Default.CallMade, contentDescription = "Send Data", tint = Color.Red)
        Text(
            text = "Send Data",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }

    // Receive Data Button
    Button(onClick = {
        navController.navigate("bluetoothDataView")

    }) {
        Icon(Icons.Default.CallReceived, contentDescription = "Receive Data", tint = Color.Green)
        Text(
            text = "Receive Data",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DisplayMessages(bluetoothViewModel: BluetoothViewModel = viewModel()) {
    val messages by bluetoothViewModel.messages.observeAsState(initial = emptyList())

    LazyColumn {
        items(messages) { message ->
            Text(message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

