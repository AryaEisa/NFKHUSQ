package com.example.nfkhusq

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfkhusq.ui.theme.NFKHUSQTheme
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nfkhusq.Permissions.BluetoothPermissions


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
class MainActivity : ComponentActivity() {
    private var bluetoothAvailable: Boolean = false
    private var bluetoothLEAvailable: Boolean = false
    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        val bluetoothConnectPermission = android.Manifest.permission.BLUETOOTH_CONNECT

        // Check if the permission is already granted (necessary for Android 12+)
        if (ContextCompat.checkSelfPermission(this, bluetoothConnectPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(bluetoothConnectPermission), REQUEST_ENABLE_BT)
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        setContent {
            //val navController = rememberNavController()
            NFKHUSQTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Navpage()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }

}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NFKHUSQTheme {

    }
}