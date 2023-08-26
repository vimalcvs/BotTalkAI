package com.vimalcvs.bottalkai.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.bounceClick
import com.vimalcvs.bottalkai.common.components.AppBar
import com.vimalcvs.bottalkai.common.components.MessageCard
import com.vimalcvs.bottalkai.common.components.TextInput
import com.vimalcvs.bottalkai.common.showRewarded
import com.vimalcvs.bottalkai.data.model.MessageModel
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.GreenShadow
import com.vimalcvs.bottalkai.ui.theme.RedShadow
import com.vimalcvs.bottalkai.ui.theme.Urbanist

@Composable
fun ChatScreen(
    navigateToBack: () -> Unit,
    navigateToUpgrade: () -> Unit,
    name: String?,
    examples: List<String>?,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val freeMessageCount by viewModel.freeMessageCount.collectAsState()
    val isProVersion by viewModel.isProVersion.collectAsState()
    val conversationId by viewModel.currentConversationState.collectAsState()
    val messagesMap by viewModel.messagesState.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getProVersion()
        viewModel.getFreeMessageCount()
    }

    val messages: List<MessageModel> =
        if (messagesMap[conversationId] == null) listOf() else messagesMap[conversationId]!!

    val paddingBottom =
        animateDpAsState(
            if (isGenerating) {
                90.dp
            } else if (viewModel.showAdsAndProVersion.value) {
                190.dp
            } else {
                0.dp
            },
            animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
        )

    val inputText = remember {
        mutableStateOf("")
    }


    Column(
        Modifier
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            AppBar(
                onClickAction = navigateToBack,
                image = R.drawable.arrow_left,
                text = if (name.isNullOrBlank()) {
                    stringResource(R.string.app_name)
                } else {
                    name
                },
                MaterialTheme.colors.surface
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (isProVersion) {
                    Text(
                        text = stringResource(R.string.pro),
                        color = MaterialTheme.colors.primary,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(GreenShadow, shape = RoundedCornerShape(90.dp))
                            .padding(horizontal = 9.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .background(GreenShadow, shape = RoundedCornerShape(90.dp))
                            .padding(horizontal = 9.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.app_icon),
                            contentDescription = "image",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .width(27.dp)
                                .height(27.dp)
                                .padding(end = 5.dp)
                        )

                        Text(
                            text = freeMessageCount.toString(),
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = Urbanist,
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
        )
        {
            if (messages.isEmpty() and viewModel.showAdsAndProVersion.value.not()) {
                if (examples.isNullOrEmpty()) {
                    Capabilities(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    )
                } else {
                    Examples(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        examples = examples,
                        inputText = inputText
                    )
                }
            } else {
                MessageList(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = paddingBottom.value), messages
                )
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AnimatedVisibility(
                    visible = isGenerating,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    content = {
                        StopButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            viewModel.stopGenerate()
                        }
                    })

                AnimatedVisibility(
                    visible = viewModel.showAdsAndProVersion.value,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    content = {
                        AdsAndProVersion(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClickWatchAd = {
                                showRewarded(context) {
                                    viewModel.showAdsAndProVersion.value = false
                                    viewModel.increaseFreeMessageCount()
                                }
                            },
                            onClickUpgrade = {
                                navigateToUpgrade()
                            })


                    })

            }
        }



        TextInput(inputText = inputText)
    }

}

@Composable
fun AdsAndProVersion(modifier: Modifier, onClickWatchAd: () -> Unit, onClickUpgrade: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        Column {
            Text(
                text = stringResource(R.string.you_reach_free_message_limit),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 20.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        color = RedShadow,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(10.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .bounceClick(onClick = onClickUpgrade)
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colors.onSecondary
                        )
                        .border(
                            2.dp,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star_vector),
                        contentDescription = stringResource(R.string.app_name),
                        tint = Green,
                        modifier = Modifier
                            .size(width = 30.dp, height = 30.dp)


                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.upgrade_to_pro),
                        color = MaterialTheme.colors.onSurface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .bounceClick(onClick = onClickWatchAd)
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colors.onSecondary
                        )
                        .border(
                            2.dp,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 15.dp, horizontal = 20.dp)
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.video),
                        contentDescription = stringResource(R.string.app_name),
                        tint = Green,
                        modifier = Modifier
                            .size(width = 30.dp, height = 30.dp)


                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.watch_ad),
                        color = MaterialTheme.colors.onSurface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        )
                    )
                }
            }
        }


    }
}

@Composable
fun StopButton(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .bounceClick(onClick = onClick)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colors.onSecondary
                )
                .border(
                    2.dp,
                    color = MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.square),
                contentDescription = stringResource(R.string.app_name),
                tint = Green,
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)


            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(id = R.string.stop_generating),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                )
            )
        }
    }
}

@Composable
fun Capabilities(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.app_name),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(width = 80.dp, height = 80.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.capabilities),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.capabilities_1),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSecondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.capabilities_2),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSecondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.capabilities_3),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSecondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.capabilities_desc),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
fun Examples(
    modifier: Modifier = Modifier,
    examples: List<String>,
    inputText: MutableState<String>
) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.type_something_like),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(examples) { example ->
                    Text(
                        text = example,
                        color = MaterialTheme.colors.onSurface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .bounceClick(
                                onClick = {
                                    inputText.value = example
                                })
                            .background(
                                color = MaterialTheme.colors.onSecondary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(20.dp)
                            .fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }


        }
    }
}

const val ConversationTestTag = "ConversationTestTag"

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messages: List<MessageModel>,
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding =
            WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize(),
            reverseLayout = true,
            state = listState,
        ) {
            items(messages.size) { index ->
                Box(modifier = Modifier.padding(bottom = if (index == 0) 10.dp else 0.dp)) {
                    Column {
                        MessageCard(
                            message = messages[index],
                            isLast = index == messages.size - 1,
                            isHuman = true
                        )
                        MessageCard(message = messages[index])
                    }
                }
            }
        }
    }
}
