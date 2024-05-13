package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import android.bluetooth.BluetoothSocket
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfkhusq.Communication.BluetoothViewModel
import com.google.ai.client.generativeai.type.content

@Composable
fun SendDataView(viewModel: BluetoothViewModel = viewModel(), navController: NavController) {
    val messages by viewModel.messages.observeAsState(initial = emptyList())
    val context = LocalContext.current
    var socket: BluetoothSocket? = null
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select a message to send:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))

        // List of received messages to choose from
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(messages) { message ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.sendData(socket ,message.content)  // Send the selected message directly
                            Toast.makeText(context, "Sent: ${message.content}", Toast.LENGTH_SHORT).show()
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${message.content} (Type: ${message.type})",
                        modifier = Modifier.weight(1f)
                    )
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

