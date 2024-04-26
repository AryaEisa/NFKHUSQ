package com.example.nfkhusq.Widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.background
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.nfkhusq.MainActivity
import com.example.nfkhusq.R
import com.example.nfkhusq.R.drawable.hqvarna


class HusqvarnaWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

            provideContent {
               ContentView()
            }

    }
}

@Composable
private fun ContentView() {
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
        Text(text = "BLE/LE", style = TextStyle(color = ColorProvider(Color.White, Color.White)))
        Image(
            provider = ImageProvider(R.drawable.hqvarna),
            contentDescription = null,
            modifier = GlanceModifier
                .fillMaxSize()

                .cornerRadius(30.dp),
            contentScale = ContentScale.Fit
            )
        Text(text = "BLE/LE scanner")
    }

}

@Preview
@Composable
private fun test() {
    ContentView()
}