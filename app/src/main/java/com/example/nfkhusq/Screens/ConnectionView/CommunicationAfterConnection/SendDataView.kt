package com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfkhusq.Communication.BluetoothViewModel

@Composable
fun SendDataView(viewModel: BluetoothViewModel = viewModel(), navController: NavController) {
    // Local state for managing user input
    var textToSend by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        // Text field for input
        BasicTextField(
            value = textToSend,
            onValueChange = { textToSend = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.sendData(textToSend)
                textToSend = ""  // Clear the text field after sending
            }),
            decorationBox = { innerTextField ->
                if (textToSend.isEmpty()) {
                    Text("Enter message to send", style = TextStyle.Default.copy(color = Color.Gray))
                }
                innerTextField()
            }
        )

        // Button to trigger sending
        Button(
            onClick = {
                viewModel.sendData(textToSend)
                textToSend = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }
    }
}
