package com.example.nfkhusq.Widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonColors
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.background
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
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
import com.example.nfkhusq.R
import com.example.nfkhusq.R.drawable.hqvarna


class HusqvarnaWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

            provideContent {
                //Todo
            }

    }
}

@Composable
private fun ContentView() {
    val context = LocalContext.current

    androidx.glance.layout.Column(
        modifier = GlanceModifier.fillMaxSize().background(day = Color.Black, night = Color.White)
            , horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(hqvarna),
            contentDescription = null,
            modifier = GlanceModifier
                .size(60.dp)
                .cornerRadius(30.dp),
            contentScale = ContentScale.Crop
            )
        Spacer(
            modifier = GlanceModifier.height(10.dp)
        )
        Text(text = "Husqvarna",
            style = TextStyle(color = ColorProvider(day = Color.Black,
                night = Color.White), fontSize = 16.sp
            )
        )
        Spacer(
            modifier = GlanceModifier.height(10.dp)
        )
        Button(
            text = "Husqvarna",
            style = TextStyle(
                fontSize = 16.sp
            ),
            onClick = {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.husqvarna.com/se/?&utm_source=google-ads&utm_medium=cpc&utm_campaign=se-residential-brand-central-google-search-intent-conversion-brand-core&utm_term=husqvarna&gad_source=1&gclid=CjwKCAjwoa2xBhACEiwA1sb1BFgfiVvTFJd2AlzBC0IIO1MXx6JpT512ViM6iAFqb-q75CzrKGEPRxoCGJcQAvD_BwE&gclsrc=aw.ds")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivities(arrayOf(this))
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = ColorProvider(day = Color.Black, night = Color.White),
                backgroundColor = ColorProvider(day = Color.Black, night = Color.White)
            ),
            modifier = GlanceModifier
                .wrapContentSize()
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .cornerRadius(2.dp)
        )
    }
}