package com.example.nfkhusq.Permissions

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import com.example.nfkhusq.ui.theme.NFKHUSQTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermission(navController: NavController) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    var isLocationPermissionGranted by remember {
        mutableStateOf(preferences.getBoolean("LOCATION_PERMISSION_GRANTED", false))
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        isLocationPermissionGranted = isGranted
        preferences.edit().putBoolean("LOCATION_PERMISSION_GRANTED", isGranted).apply()
        if (isGranted) {
            Log.d("LocationPermission", "Location permission granted.")
        } else {
            Toast.makeText(context, "Location permission is required for proper app functionality.", Toast.LENGTH_LONG).show()
            Log.d("LocationPermission", "Location permission denied.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Permissions") },
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
            Button(
                onClick = {
                    if (!isLocationPermissionGranted) {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = !isLocationPermissionGranted
                ,
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Request Location Permission",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isLocationPermissionGranted) "Permission Granted" else "Permission Not Granted",
                color = if (isLocationPermissionGranted) Color.Green else Color.Red,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationhPermission() {
    NFKHUSQTheme {
        // Assuming 'navController' is a stub for preview purposes
        val navController = NavController(LocalContext.current)
        LocationPermission(navController)
    }
}
