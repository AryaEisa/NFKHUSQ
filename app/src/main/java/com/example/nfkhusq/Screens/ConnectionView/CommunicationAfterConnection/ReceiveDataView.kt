package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecieveDataView(bluetoothViewModel: BluetoothViewModel = viewModel(), navController: NavController) {
    // Default value is necessary unless your LiveData is guaranteed to be non-null
    val messages by bluetoothViewModel.messages.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bluetooth Data Receiver") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(messages) { message ->
                        Text(
                            text = message,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Adding a "Receive Data" button
                Button(
                    onClick = {
                        bluetoothViewModel.receiveData()  // This will trigger data reception
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Receive Data")
                }

                Button(
                    onClick = { bluetoothViewModel.clearMessages() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Messages")
                }
            }
        }
    )
}

