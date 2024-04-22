package com.example.nfkhusq

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nfkhusq.Bluetooth.BluetoothLeScanner
import com.example.nfkhusq.ui.theme.NFKHUSQTheme


/*
Den första raden kontrollerar om klassisk Bluetooth är tillgängligt
med hjälp av metoden hasSystemFeature i PackageManager-klassen.
Metoden tar en konstant som anger vilken funktion som ska kontrolleras,
i det här fallet FEATURE_BLUETOOTH. Om funktionen är tillgänglig
returnerar metoden true, annars false.

Den andra raden kontrollerar om Bluetooth Low Energy (BLE) är
tillgängligt med hjälp av samma metod, men denna gång med konstanten
FEATURE_BLUETOOTH_LE. Även här returnerar metoden true om funktionen
är tillgänglig och false om den inte är det.
 */
@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        val bluetoothScanPermission = android.Manifest.permission.BLUETOOTH_SCAN

        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, bluetoothScanPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(bluetoothScanPermission), REQUEST_BLUETOOTH_SCAN)
        }

        // Check if Bluetooth is enabled
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        setContent {
            NFKHUSQTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BluetoothLeScanner()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_BLUETOOTH_SCAN -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, perform Bluetooth scanning or other actions
                } else {
                    // Permission denied, handle accordingly
                }
            }
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val REQUEST_BLUETOOTH_SCAN = 2
    }
}







@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NFKHUSQTheme {

    }
}