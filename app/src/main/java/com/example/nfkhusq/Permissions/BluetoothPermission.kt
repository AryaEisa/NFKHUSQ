package com.example.nfkhusq.Permissions

import android.Manifest
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.ui.theme.NFKHUSQTheme
import timber.log.Timber


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothPermissions(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    var isBluetoothPermissionGranted by remember {
        mutableStateOf(sharedPreferences.getBoolean("BLUETOOTH_PERMISSION_GRANTED", false))
    }
    val bluetoothPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.BLUETOOTH_CONNECT] ?: false
        isBluetoothPermissionGranted = granted
        sharedPreferences.edit().putBoolean("BLUETOOTH_PERMISSION_GRANTED", granted).apply()
        if (granted) {
            Timber.d("BluetoothPermissions", "Bluetooth connect permission granted.")
        } else {
            Toast.makeText(context, "Bluetooth connect permission is required.", Toast.LENGTH_LONG).show()
            Timber.d("BluetoothPermissions", "Bluetooth connect permission not granted.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth Permissions") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Bluetooth permissions status: $isBluetoothPermissionGranted",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(
                onClick = {
                    if (!isBluetoothPermissionGranted) {
                        bluetoothPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                    }
                },
                enabled = !isBluetoothPermissionGranted,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBluetoothPermissionGranted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F) else MaterialTheme.colorScheme.primary,
                    contentColor = if (isBluetoothPermissionGranted) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3F) else MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Request Bluetooth Permissions",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                if (isBluetoothPermissionGranted) "Permission Granted" else "Permission Not Granted",
                color = if (isBluetoothPermissionGranted) Color.Green else Color.Red,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (isBluetoothPermissionGranted) {
                Button(
                    onClick = {
                        Timber.d("Navigation", "Attempting to navigate to BluetoothLEScanner")
                        navController.navigate("BluetoothLEScanner")
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Go to Bluetooth Scanner", fontWeight = FontWeight.Bold)
                }

            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun PrevBluetoothPermission() {
    NFKHUSQTheme {
        BluetoothPermissions(navController = rememberNavController())
    }
}