package com.vimalcvs.bottalkai.ui.startchat

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.components.AnimatedButton
import com.vimalcvs.bottalkai.common.components.AppBar
import com.vimalcvs.bottalkai.common.components.NoConnectionDialog
import com.vimalcvs.bottalkai.common.components.ThereIsUpdateDialog
import com.vimalcvs.bottalkai.ui.activity.isOnline
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.GreenShadow
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.upgrade.PurchaseHelper
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun StartChatScreen(
    navigateToChat: (String, String, List<String>?) -> Unit,
    navigateToUpgrade: () -> Unit,
    startChatViewModel: StartChatViewModel = hiltViewModel(),
    purchaseHelper: PurchaseHelper
) {
    fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

    }

    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }
    var showUpdateDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        startChatViewModel.getCurrentLanguageCode()
        val currentLanguageCode = startChatViewModel.currentLanguageCode.value

        changeLanguage(context, currentLanguageCode)


        purchaseHelper.checkPurchase {
            if (it.not()) {
                startChatViewModel.setProVersion(false)
            }
        }
        startChatViewModel.getFirstTime()
        startChatViewModel.getProVersion()
        startChatViewModel.isThereUpdate()


        delay(500)

        if (startChatViewModel.isProVersion.value.not() && startChatViewModel.isFirstTime.value && isOnline(
                context
            ) && startChatViewModel.isThereUpdate.value.not()
        ) {
            startChatViewModel.setFirstTime(false)
            navigateToUpgrade()
        }

        if (isOnline(context).not()) {
            showDialog = true
        }

        if (startChatViewModel.isThereUpdate.value) {
            showUpdateDialog = true
        }
    }


    if (showDialog) {
        NoConnectionDialog {
            showDialog = false
        }
    }

    if (showUpdateDialog) {
        ThereIsUpdateDialog {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.vimalcvs.bottalkai")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.vimalcvs.bottalkai")
                    )
                )
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(
            onClickAction = {},
            image = R.drawable.app_icon,
            text = stringResource(R.string.app_name),
            Green
        )
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.app_name),
                tint = Green,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (startChatViewModel.isProVersion.value.not()) {
                Text(
                    text = stringResource(R.string.welcome_to),
                    color = MaterialTheme.colors.surface,
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = Urbanist,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.welcome_app_name),
                    color = Green,
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = Urbanist,
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center
                )
            } else {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(R.string.pro),
                        color = MaterialTheme.colors.primary,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(GreenShadow, shape = RoundedCornerShape(90.dp))
                            .padding(horizontal = 9.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(R.string.welcome_description),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))

            AnimatedButton(
                onClick = {
                    navigateToChat(
                        "",
                        "",
                        null
                    )
                },
                text = stringResource(R.string.start_chat)
            )

        }
    }

}
