package com.example.nfkhusq.Widget

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.background
import androidx.glance.appwidget.cornerRadius
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.ContentScale
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.nfkhusq.MainActivity
import com.example.nfkhusq.R


@Composable
fun ContentView() {
    val context = LocalContext.current

    androidx.glance.layout.Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(day = Color.Black, night = Color.Black)
            .clickable(
                onClick = actionStartActivity(
                    Intent(context, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "BLE/LE",
            style = TextStyle(
                color = ColorProvider(Color.White, Color.White),
                fontSize = 18.sp,  // Consider making this dynamic based on widget size
                fontWeight = FontWeight.Bold
            )
        )
        Image(
            provider = ImageProvider(R.drawable.hqvarna),
            contentDescription = "Husqvarna Logo",
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp)  // Add padding to ensure the image does not touch the edges
                .cornerRadius(30.dp),
            contentScale = ContentScale.Fit  // Ensures the image fits within the widget without cropping
        )
    }
}
