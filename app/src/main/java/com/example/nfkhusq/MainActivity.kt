package com.example.nfkhusq

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
class MainActivity : ComponentActivity() {
    private var bluetoothAvailable: Boolean = false
    private var bluetoothLEAvailable: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val packageManager = packageManager
        bluetoothAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)
        bluetoothLEAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)

        setContent {
            NFKHUSQTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(true) }
    Switch(
        checked = isChecked,
        onCheckedChange = { isChecked = !isChecked },
        thumbContent = {
            Icon(imageVector = Icons.Default.Check, contentDescription = stringResource(id = androidx.compose.ui.R.string.selected))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NFKHUSQTheme {
        Greeting("Android")
    }
}