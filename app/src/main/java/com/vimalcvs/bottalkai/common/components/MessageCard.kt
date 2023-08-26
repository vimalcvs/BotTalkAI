package com.vimalcvs.bottalkai.common.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.CodeBlockStyle
import com.halilibo.richtext.ui.RichTextStyle
import com.halilibo.richtext.ui.TableStyle
import com.halilibo.richtext.ui.material.MaterialRichText
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.data.model.MessageModel
import com.vimalcvs.bottalkai.ui.theme.CodeBackground
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.Typography
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.theme.White

@Composable
fun MessageCard(message: MessageModel, isHuman: Boolean = false, isLast: Boolean = false) {
    Column(
        horizontalAlignment = if (isHuman) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = if (isHuman) 0.dp else 10.dp)
            .padding(start = if (isHuman) 10.dp else 0.dp)
            .padding(vertical = 4.dp)
            .padding(top = if (isLast) 50.dp else 0.dp)
    ) {
        if (isHuman) {
            HumanMessageCard(message = message)
        } else {
            BotMessageCard(message = message)
        }
    }
}

@Composable
fun HumanMessageCard(message: MessageModel) {
    Box(
        modifier = Modifier
            .widthIn(0.dp)
            .background(
                Green,
                shape = RoundedCornerShape(
                    topEnd = 16.dp,
                    topStart = 16.dp,
                    bottomEnd = 5.dp,
                    bottomStart = 16.dp
                )
            ),
    ) {
        Text(
            text = message.question,
            color = White,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            textAlign = TextAlign.End,
            style = Typography.body1
        )
    }


}

@Composable
fun BotMessageCard(message: MessageModel) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message.answer.trimIndent())
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var expanded by remember { mutableStateOf(false) }


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {

        Box(
            modifier = Modifier
                .widthIn(0.dp)
                .background(
                    MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(
                        topEnd = 16.dp,
                        topStart = 5.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            expanded = true
                        }
                    )
                },
        ) {
            MaterialRichText(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                style = RichTextStyle(
                    codeBlockStyle = CodeBlockStyle(
                        textStyle = TextStyle(
                            fontFamily = Urbanist,
                            fontSize = 14.sp,
                            color = White
                        ),
                        wordWrap = true,
                        modifier = Modifier.background(
                            shape = RoundedCornerShape(6.dp),
                            color = CodeBackground,
                        )
                    ),
                    tableStyle = TableStyle(borderColor = MaterialTheme.colors.surface),
                )
            ) {
                Markdown(
                    content = message.answer.trimIndent()
                )
            }
        }
        MaterialTheme(
            colors = MaterialTheme.colors.copy(surface = MaterialTheme.colors.surface),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colors.onSecondary, RoundedCornerShape(16.dp))
                    .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(16.dp)),
                properties = PopupProperties(focusable = false)
            ) {
                DropdownMenuItem(
                    onClick = {
                        clipboardManager.setText(AnnotatedString((message.answer.trimIndent())))
                        expanded = false
                    }
                ) {
                    Icon(
                        painterResource(R.drawable.copy),
                        "copyMessage",
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colors.surface,
                    )
                    Text(
                        text = stringResource(R.string.copy),
                        color = MaterialTheme.colors.surface,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = Typography.body1
                    )
                }
                Divider(
                    color = MaterialTheme.colors.secondary, thickness = 1.dp,
                )
                DropdownMenuItem(
                    onClick = {
                        context.startActivity(shareIntent)
                        expanded = false
                    }
                )
                {
                    Icon(
                        painterResource(R.drawable.share),
                        "shareMessage",
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colors.surface,
                    )
                    Text(
                        text = stringResource(R.string.share),
                        color = MaterialTheme.colors.surface,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = Typography.body1
                    )

                }

            }
        }
//            Spacer(modifier = Modifier.width(10.dp))
//
//            Column {
//                IconButton(
//                    modifier = Modifier.size(25.dp),
//                    onClick = {
//
//                    }) {
//                    Icon(
//                        painterResource(R.drawable.copy),
//                        "copyMessage",
//                        modifier = Modifier.size(25.dp),
//                        tint = IconColor,
//                    )
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                IconButton(
//                    modifier = Modifier.size(25.dp),
//                    onClick = {
//
//                    }) {
//                    Icon(
//                        painterResource(R.drawable.share),
//                        "shareMessage",
//                        modifier = Modifier.size(25.dp),
//                        tint = IconColor,
//                    )
//                }
//            }


    }


}