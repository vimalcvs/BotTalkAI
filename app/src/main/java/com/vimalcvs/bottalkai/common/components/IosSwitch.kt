package com.vimalcvs.bottalkai.common.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vimalcvs.bottalkai.common.click
import com.vimalcvs.bottalkai.ui.settings.SettingsViewModel
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.White

@Composable
fun IosSwitch(
    switchOn: MutableState<Boolean>,
    width: Dp = 45.dp,
    height: Dp = 27.dp,
    checkedTrackColor: Color = Green,
    uncheckedTrackColor: Color = MaterialTheme.colors.secondary,
    gapBetweenThumbAndTrackEdge: Dp = 2.dp,
    cornerSize: Int = 50,
    iconInnerPadding: Dp = 2.dp,
    thumbSize: Dp = 22.dp,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {


    // state of the switch
    var switchOnCurrent by remember {
        mutableStateOf(switchOn.value)
    }

    // for moving the thumb
    val alignment by animateAlignmentAsState(if (switchOnCurrent) 1f else -1f)

    // outer rectangle with border
    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .background(
                color = if (switchOnCurrent) checkedTrackColor else uncheckedTrackColor,
                shape = CircleShape
            )
            .click {
                switchOnCurrent = !switchOnCurrent
                switchOn.value = !switchOn.value
                settingsViewModel.setDarkMode(switchOn.value)
            },
        contentAlignment = Alignment.Center
    ) {

        // this is to add padding at the each horizontal side
        Box(
            modifier = Modifier
                .padding(
                    start = gapBetweenThumbAndTrackEdge,
                    end = gapBetweenThumbAndTrackEdge
                )
                .fillMaxSize(),
            contentAlignment = alignment
        ) {

            // thumb with icon
            Box(
                modifier = Modifier
                    .size(size = thumbSize)
                    .background(
                        color = White,
                        shape = CircleShape
                    )
                    .padding(all = iconInnerPadding),
            )
        }
    }


}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}