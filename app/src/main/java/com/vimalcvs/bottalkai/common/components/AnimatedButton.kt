package com.vimalcvs.bottalkai.common.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vimalcvs.bottalkai.common.bounceClick
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.theme.White
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer


@Composable
fun AnimatedButton(onClick: () -> Unit = {}, text: String) {
    val creditCardTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 1_000,
                easing = LinearEasing,
            ),
        ),
        blendMode = BlendMode.Hardlight,
        rotation = 25f,
        shaderColors = listOf(
            Color.White.copy(alpha = 0.0f),
            Color.White.copy(alpha = 0.6f),
            Color.White.copy(alpha = 0.0f),
        ),
        shaderColorStops = null,
        shimmerWidth = 150.dp,
    )

    CompositionLocalProvider(
        LocalShimmerTheme provides creditCardTheme
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .padding(15.dp)
                .bounceClick(onClick = onClick),
            elevation = 5.dp,
            backgroundColor = Green,
            shape = RoundedCornerShape(90.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .shimmer(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = White,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = Urbanist
                    ),
                    textAlign = TextAlign.Center
                )

            }
        }
    }


}