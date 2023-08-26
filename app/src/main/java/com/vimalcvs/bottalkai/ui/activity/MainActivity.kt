package com.vimalcvs.bottalkai.ui.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.firebase.messaging.FirebaseMessaging
import com.vimalcvs.bottalkai.common.loadRewarded
import com.vimalcvs.bottalkai.common.removeInterstitial
import com.vimalcvs.bottalkai.navigation.BottomNavigationBar
import com.vimalcvs.bottalkai.navigation.NavGraph
import com.vimalcvs.bottalkai.navigation.Screen
import com.vimalcvs.bottalkai.ui.theme.BotTalkAITheme
import com.vimalcvs.bottalkai.ui.upgrade.PurchaseHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    val CHANNEL_ID = "BotTalkAI"
    val CHANNEL_NAME = "BotTalkAI"
    val NOTIF_ID = 0

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        loadRewarded(this)
        val purchaseHelper = PurchaseHelper(this)
        purchaseHelper.billingSetup()


        FirebaseMessaging.getInstance().subscribeToTopic("all").addOnSuccessListener {
        }
        askNotificationPermission()
        createNotificationChannel()

        setContent {

//            viewModel.getCurrentLanguageCode()
//            val currentLanguageCode by viewModel.currentLanguageCode.collectAsState()
//
//            val locale = Locale(currentLanguageCode)
//            val config = Configuration()
//            config.setLocale(locale)
//            val resources = this@MainActivity.resources
//            resources?.updateConfiguration(config, resources.displayMetrics)

            val darkThemeCurrent by viewModel.darkMode.collectAsState()
            val darkTheme = remember { mutableStateOf(darkThemeCurrent) }

            BotTalkAITheme(darkTheme = darkTheme.value) {

                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberAnimatedNavController(bottomSheetNavigator)

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Screen.Upgrade.route -> bottomBarState.value = false
                    Screen.Splash.route -> bottomBarState.value = false
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" -> bottomBarState.value =
                        false

                    null -> bottomBarState.value = false
                    else -> bottomBarState.value = true
                }



                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = RoundedCornerShape(
                        topStart = 35.dp,
                        topEnd = 35.dp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .captionBarPadding()
                            .imePadding()
                            .statusBarsPadding(),
                    )
                    {
                        NavGraph(
                            navController = navController,
                            bottomBarState,
                            darkMode = darkTheme,
                            purchaseHelper
                        )
                        Column(
                            Modifier
                                .fillMaxHeight(),
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            BottomNavigationBar(navController, bottomBarState)
                        }
                    }

                }
            }

        }
    }

    override fun onDestroy() {
        removeInterstitial()
        super.onDestroy()
    }

}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}
