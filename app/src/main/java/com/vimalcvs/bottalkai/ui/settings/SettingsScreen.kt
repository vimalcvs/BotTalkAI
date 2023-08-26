package com.vimalcvs.bottalkai.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.click
import com.vimalcvs.bottalkai.common.components.AppBar
import com.vimalcvs.bottalkai.common.components.IosSwitch
import com.vimalcvs.bottalkai.common.components.UpgradeButton
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.upgrade.PurchaseHelper
import com.yagmurerdogan.toasticlib.Toastic


@Composable
fun SettingsScreen(
    darkMode: MutableState<Boolean>,
    navigateToLanguages: () -> Unit,
    navigateToUpgrade: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    purchaseHelper: PurchaseHelper
) {

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    var showSuccessToast by remember {
        mutableStateOf(false)
    }
    var showErrorToast by remember {
        mutableStateOf(false)
    }

    if (showSuccessToast) {
        Toastic
            .toastic(
                context = context,
                message = context.resources.getString(R.string.welcome_to_pro_version),
                duration = Toastic.LENGTH_LONG,
                type = Toastic.SUCCESS,
                isIconAnimated = true
            )
            .show()
        showSuccessToast = false
    }

    if (showErrorToast) {
        Toastic
            .toastic(
                context = context,
                message = context.resources.getString(R.string.pro_version_not_purchased),
                duration = Toastic.LENGTH_LONG,
                type = Toastic.ERROR,
                isIconAnimated = true
            )
            .show()
        showErrorToast = false
    }

    LaunchedEffect(Unit) {
        settingsViewModel.getProVersion()
        settingsViewModel.getCurrentLanguage()
    }

    val language by settingsViewModel.currentLanguage.collectAsState()

    Column(Modifier.fillMaxSize()) {
        AppBar(
            onClickAction = {},
            image = R.drawable.app_icon,
            text = stringResource(R.string.settings),
            Green
        )
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp)
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(R.drawable.food),
//                contentDescription = stringResource(R.string.app_name),
//                modifier = Modifier
//                    .size(width = 80.dp, height = 80.dp)
//                    .background(
//                        shape = RoundedCornerShape(90.dp),
//                        color = MaterialTheme.colors.secondary
//                    )
//                    .padding(10.dp)
//            )
//            Spacer(modifier = Modifier.width(10.dp))
//            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
//                Text(
//                    text = "Murat ÖZTÜRK",
//                    color = MaterialTheme.colors.surface,
//                    style = TextStyle(
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.W700,
//                        fontFamily = Urbanist,
//                        lineHeight = 25.sp
//                    )
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = "murat318ozturk@gmail.com",
//                    color = MaterialTheme.colors.onSurface,
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.W600,
//                        fontFamily = Urbanist,
//                        lineHeight = 25.sp
//                    )
//                )
//            }
//            Spacer(modifier = Modifier.width(10.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.right),
//                contentDescription = null,
//                tint = MaterialTheme.colors.surface,
//                modifier = Modifier
//                    .padding(start = 5.dp)
//                    .size(30.dp)
//            )
//        }

        if (settingsViewModel.isProVersion.value.not()) {
            UpgradeButton(onClick = { navigateToUpgrade() })
        }


        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.general),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.dp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .click {
                    navigateToLanguages()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.more_circle),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.language),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = language,
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                )
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.right),
                contentDescription = null,
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(30.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.dark_theme),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.dark_theme),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(20.dp))
            IosSwitch(darkMode)
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.purchase),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.dp,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .click {
                    purchaseHelper.restorePurchase {
                        if (it) {
                            settingsViewModel.setProVersion(true)
                            showSuccessToast = true
                        } else {
                            showErrorToast = true
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.buy),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.restore_purchase),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.right),
                contentDescription = null,
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(30.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.about),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.dp,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .click {
                    uriHandler.openUri(Constants.HELP)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.paper),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.help_center),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.right),
                contentDescription = null,
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(30.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .click {
                    uriHandler.openUri(Constants.PRIVACY_POLICY)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.shield_done),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.privacy_policy),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.right),
                contentDescription = null,
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(30.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .click {
                    uriHandler.openUri(Constants.ABOUT)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.info_square),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.about_us),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                painter = painterResource(id = R.drawable.right),
                contentDescription = null,
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(30.dp)
            )
        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp)
//                .padding(horizontal = 16.dp)
//                .clickable(
//                    interactionSource = interactionSource,
//                    indication = null,
//                ) { navigateToLogout() },
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.logout),
//                tint = Red,
//                contentDescription = stringResource(R.string.app_name),
//                modifier = Modifier
//                    .size(width = 27.dp, height = 27.dp)
//
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Text(
//                text = stringResource(id = R.string.logout),
//                color = Red,
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = Urbanist,
//                    lineHeight = 25.sp
//                ),
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(15.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.right),
//                contentDescription = null,
//                tint = Red,
//                modifier = Modifier
//                    .padding(start = 5.dp)
//                    .size(30.dp)
//            )
//        }
    }
}


