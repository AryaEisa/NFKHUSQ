package com.example.nfkhusq

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.Permissions.BluetoothPermissions
import com.example.nfkhusq.Permissions.LocationPermission
import com.example.nfkhusq.Screens.BluetoothLeScanner


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Navpage() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "startPage") {
        composable("startPage") { StartPage(navController) }
        composable("LocationPermission") { LocationPermission(navController) }
        composable("BluetoothPermission") { BluetoothPermissions(navController) }
        composable("BluetoothLEScanner") { BluetoothLeScanner() }
        // Default or error composable
        composable("error") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Page not found", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}



