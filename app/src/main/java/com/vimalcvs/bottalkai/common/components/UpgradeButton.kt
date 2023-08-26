package com.vimalcvs.bottalkai.common.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.bounceClick
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.theme.White
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer


@Composable
fun UpgradeButton(onClick: () -> Unit = {}) {
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

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.star))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )


    CompositionLocalProvider(
        LocalShimmerTheme provides creditCardTheme
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
                .padding(horizontal = 16.dp)
                .bounceClick(onClick = onClick),
            elevation = 5.dp,
            backgroundColor = Green,
            shape = RoundedCornerShape(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(width = 60.dp, height = 60.dp)
                        .background(White, shape = RoundedCornerShape(90.dp))
                )

                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(id = R.string.upgrade_to_pro),
                        color = White,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.upgrade_to_pro_description),
                        color = White,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.right),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(30.dp)
                )
            }
        }
    }


}