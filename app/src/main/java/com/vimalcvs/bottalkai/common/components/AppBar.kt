package com.vimalcvs.bottalkai.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vimalcvs.bottalkai.ui.theme.Urbanist

@Composable
fun AppBar(
    onClickAction: () -> Unit,
    image: Int,
    text: String,
    tint: Color,
    menuItems: (@Composable () -> Unit)? = null
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
    {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            color = MaterialTheme.colors.surface,
            style = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                fontFamily = Urbanist,
                textAlign = TextAlign.Center
            )
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            IconButton(
                onClick = onClickAction,
                modifier = Modifier
                    .width(27.dp)
                    .height(27.dp)
            ) {

                Icon(
                    painter = painterResource(image),
                    contentDescription = "image",
                    tint = tint,
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (menuItems != null) {
                menuItems()
            }

        }
    }


}