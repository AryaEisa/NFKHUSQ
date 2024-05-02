package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import android.bluetooth.BluetoothSocket
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothDataView(bluetoothViewModel: BluetoothViewModel = viewModel(), navController: NavController) {
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


// ViewModel should be part of your architecture to hold and manage UI-related data in a lifecycle conscious way.
class BluetoothViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<String>>(listOf())
    val messages: LiveData<List<String>> = _messages

    // Use synchronized if accessing from multiple threads to avoid race conditions
    fun receiveMessage(message: String) {
        val currentList = _messages.value ?: emptyList()
        _messages.value = currentList + message // Creates a new list including the new message
    }
    // Method to receive data from Bluetooth
    fun receiveData() {
        // Example to add received data
        // In reality, you would use Bluetooth API callbacks to update this
        CoroutineScope(Dispatchers.IO).launch {
            val receivedData = listenToBluetoothDevice() // This method needs to be implemented based on your Bluetooth setup
            _messages.postValue(receivedData)
        }
    }

    private fun listenToBluetoothDevice(): List<String> {
        // This method should interact with the Bluetooth API to receive data
        // Return the data as a list of strings
        return listOf("Sample Data 1", "Sample Data 2") // Placeholder for actual Bluetooth data
    }
    private fun getConnectedSocket(): BluetoothSocket? {
        // Implement your logic to obtain the connected BluetoothSocket here
        // This could involve establishing a Bluetooth connection with a device and obtaining the socket
        // Return the connected BluetoothSocket or null if the connection is not established
        return null
    }
    fun clearMessages() {
        _messages.value = emptyList()
    }
}
