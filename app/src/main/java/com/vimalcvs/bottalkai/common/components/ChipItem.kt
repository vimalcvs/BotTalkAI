package com.vimalcvs.bottalkai.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vimalcvs.bottalkai.common.bounceClick
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.theme.White

@Composable
fun ChipItem(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) White else Green,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            fontFamily = Urbanist,
            lineHeight = 25.sp
        ), modifier = Modifier
            .padding(5.dp)
            .bounceClick(onClick = {
                onClick()
            })
            .background(
                shape = RoundedCornerShape(90.dp),
                color = if (selected) Green else Color.Transparent
            )
            .border(2.dp, color = Green, shape = RoundedCornerShape(90.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp)
    )


}
