package com.example.nfkhusq

import LocationhPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.Surface
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nfkhusq.Permissions.BluetoothPermissions


@Composable
fun Navpage() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "startPage") {
        composable("startPage") { StartPage(navController) }
        composable("LocationPermission") { LocationhPermission(navController) }
        composable("BluetoothPermission") { BluetoothPermissions(navController) }
        // Default or error composable
        composable("error") {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Page not found", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}



fun NavController.safeNavigate(route: String) {
    val destination = graph.matchDeepLink(NavDeepLinkRequest.Builder.fromUri(Uri.parse(route)).build())
    if (destination != null) {
        this.navigate(route)
    } else {
        Log.e("Navigation", "No navigation route found for: $route")
        // Optionally handle the error, e.g., by showing an error message or navigating to a default screen
    }
}
