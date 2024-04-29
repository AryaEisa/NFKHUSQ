package com.example.nfkhusq.Screens

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.nfkhusq.Connection.getConnectedDevices
import com.example.nfkhusq.R
import java.io.File



@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun InfoPage(navController: NavController) {
    val context = LocalContext.current
    val connectedDevices = remember { getConnectedDevices() }

    // Function to generate a file containing the information to be downloaded
    fun generateDownloadableFile(): File {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "connected_devices.txt")
        file.writeText(connectedDevices.joinToString(separator = "\n"))
        return file
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top, // Align header to top
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // Add padding for content
        ) {
            Text(
                text = "Connected Devices",
                style = MaterialTheme.typography.bodySmall, // Use h6 for section header
                modifier = Modifier.padding(horizontal = 16.dp), // Add horizontal padding for title
                color = Color.White
            )
            if (connectedDevices.isEmpty()) {
                // Display a placeholder message for no devices
                Text(
                    text = "No connected devices found.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.run { padding(horizontal = 16.dp) },
                    color = Color.White
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(connectedDevices) { device ->
                        DeviceInfo(navController = navController, device = device )
                        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between devices
                        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)) // Use a lighter divider
                    }
                }
            }
        }
    }
}


// Function to download the file using Android's DownloadManager
@SuppressLint("ServiceCast")
fun downloadFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.file-provider", file)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(uri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setTitle("Downloading")
        setDescription("Downloading ${file.name}")
        setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.name)
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }
    downloadManager.enqueue(request)
}





