@file:Suppress("DEPRECATION")

package com.example.nfkhusq

import android.bluetooth.BluetoothDevice
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.Communication.BluetoothViewModel
import com.example.nfkhusq.Connection.connectedDeviceList
import com.example.nfkhusq.Permissions.BluetoothPermissions
import com.example.nfkhusq.Permissions.LocationPermission
import com.example.nfkhusq.Screens.ConnectionView.BluetoothLeScanner
import com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection.RecieveDataView
//import com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection.BluetoothViewModel
import com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection.SendDataToDevice
import com.example.nfkhusq.Screens.ConnectionView.CommunicationAfterConnection.SendDataView
import com.example.nfkhusq.Screens.ConnectionView.DeviceDetails
import com.example.nfkhusq.Screens.ConnectionView.InfoPage


fun getDeviceByAddress(address: String?): BluetoothDevice? {
    return connectedDeviceList.find { it.address == address }
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Navpage(bluetoothViewModel: BluetoothViewModel = viewModel()) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "startPage") {
        composable("startPage") { StartPage(navController) }
        composable("LocationPermission") { LocationPermission(navController) }
        composable("BluetoothPermission") { BluetoothPermissions(navController) }
        composable("InfoPage"){ InfoPage(navController) }
        composable("BluetoothLEScanner") { BluetoothLeScanner(navController) }
        composable("deviceDetails/{deviceAddress}") { backStackEntry ->
            // Retrieve the device address from the navigation argument
            val deviceAddress = backStackEntry.arguments?.getString("deviceAddress")
            val device = getDeviceByAddress(deviceAddress)
            device?.let {
                DeviceDetails(it, navController)
            }
        }
        composable("RecieveDataView"){ RecieveDataView(navController = navController)}
        composable("SendDataView"){ SendDataView(navController = navController) }
        composable("sendDataToDevice/{deviceAddress}") { backStackEntry ->
            // Retrieve the device address from the navigation argument
            val deviceAddress = backStackEntry.arguments?.getString("deviceAddress")
            val device = getDeviceByAddress(deviceAddress)
            device?.let {
                SendDataToDevice(it, viewModel(), navController)
            }
        }
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



