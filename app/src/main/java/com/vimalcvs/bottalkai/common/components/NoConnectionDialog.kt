package com.vimalcvs.bottalkai.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.bounceClick
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.GreenShadow
import com.vimalcvs.bottalkai.ui.theme.Urbanist


@Composable
fun NoConnectionDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background, RoundedCornerShape(35.dp))
                .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.network_error),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            Divider(
                color = MaterialTheme.colors.secondary,
                thickness = 1.dp,
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = stringResource(R.string.no_conection_try_again),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            Row(modifier = Modifier.padding(vertical = 20.dp)) {
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f)
                        .bounceClick {
                            onDismiss()
                        },
                    elevation = 0.dp,
                    backgroundColor = GreenShadow,
                    shape = RoundedCornerShape(90.dp),
                ) {
                    Row(
                        Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Green,
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
    }
}

