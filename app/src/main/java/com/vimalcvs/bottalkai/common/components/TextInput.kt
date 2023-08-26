package com.vimalcvs.bottalkai.common.components

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.ui.activity.isOnline
import com.vimalcvs.bottalkai.ui.chat.ChatViewModel
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.GreenShadow
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.theme.White
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun TextInput(
    viewModel: ChatViewModel = hiltViewModel(),
    inputText: MutableState<String>
) {
    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        NoConnectionDialog {
            showDialog = false
        }
    }

    val isGenerating by viewModel.isGenerating.collectAsState()
    val freeMessageCount by viewModel.freeMessageCount.collectAsState()

    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var hasFocus by remember { mutableStateOf(false) }
    text = text.copy(text = inputText.value)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            inputText.value = result?.get(0).toString()


        }
    }

    Box(
        // Use navigationBarsPadding() imePadding() and , to move the input panel above both the
        // navigation bar, and on-screen keyboard (IME)
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .background(MaterialTheme.colors.background),
    ) {
        Column {
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.dp,
            )
            Box(
                Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Row(Modifier.padding(all = 5.dp), verticalAlignment = Alignment.Bottom) {

                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            inputText.value = it.text
                            text = it
                        },
                        label = null,
                        placeholder = {
                            Text(
                                stringResource(R.string.ask_me_anything),
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.onSurface,
                                fontFamily = Urbanist,
                                fontWeight = FontWeight.W600
                            )
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.surface,
                            fontSize = 16.sp,
                            fontFamily = Urbanist,
                            fontWeight = FontWeight.W600
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 50.dp)
                            .heightIn(max = 120.dp)
                            .padding(end = 18.dp)
                            .weight(1f)
                            .border(
                                1.dp,
                                if (hasFocus) Green else Color.Transparent,
                                RoundedCornerShape(16.dp)
                            )
                            .onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colors.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            backgroundColor = if (hasFocus) GreenShadow else MaterialTheme.colors.secondary
                        )
                    )

                    IconButton(
                        onClick = {
                            scope.launch {
                                if (text.text.isNotEmpty()) {

                                    if (isOnline(context).not()) {
                                        showDialog = true
                                        return@launch
                                    }

                                    if (isGenerating.not()) {
                                        val textClone = text.text
                                        if (textClone.isNotBlank()) {

                                            if (viewModel.isProVersion.value.not()) {

                                                if (freeMessageCount > 0) {
                                                    viewModel.decreaseFreeMessageCount()
                                                } else {
                                                    viewModel.showAdsAndProVersion.value = true
                                                    return@launch
                                                }
                                            }

                                            viewModel.sendMessage(textClone)
                                            text = TextFieldValue("")
                                            inputText.value = ""
                                        }
                                    }

                                } else {
                                    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                                        Toast.makeText(
                                            context,
                                            "Speech not Available",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        val intent =
                                            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                                        intent.putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                            RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                                        )
                                        intent.putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE,
                                            Locale.getDefault()
                                        )
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk")
                                        launcher.launch(intent)
                                    }
                                }
                            }
                        }, modifier = Modifier
                            .size(50.dp)
                            .background(color = Green, shape = RoundedCornerShape(90.dp))

                    ) {
                        Icon(
                            if (text.text.isNotEmpty()) painterResource(R.drawable.send) else painterResource(
                                R.drawable.voice
                            ),
                            "sendMessage",
                            modifier = Modifier.size(25.dp),
                            tint = White,
                        )
                    }
                }
            }
        }
    }
}