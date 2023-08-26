package com.vimalcvs.bottalkai.ui.history

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.bounceClick
import com.vimalcvs.bottalkai.common.components.AppBar
import com.vimalcvs.bottalkai.common.toFormattedDate
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.GreenShadow
import com.vimalcvs.bottalkai.ui.theme.Red
import com.vimalcvs.bottalkai.ui.theme.Urbanist
import com.vimalcvs.bottalkai.ui.theme.White

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    navigateToChat: (String, String, List<String>?, String) -> Unit,
    navigateToDeleteConversations: () -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    savedStateHandle: SavedStateHandle? = null
) {


    if (savedStateHandle?.get<Boolean>(Constants.IS_DELETE) == true) {
        historyViewModel.deleteAllConversation()
        savedStateHandle.remove<Boolean>(Constants.IS_DELETE)
    }


    val conversations by historyViewModel.conversationsState.collectAsState()
    val isFetching by historyViewModel.isFetching.collectAsState()
    val darkMode by historyViewModel.darkMode.collectAsState()


    LaunchedEffect(true) {
        historyViewModel.getConversations()
        historyViewModel.getDarkMode()

    }

    var searchState by remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }


    var rotationState by remember { mutableStateOf(0f) }
    val rotation by animateFloatAsState(
        targetValue = rotationState - 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    LaunchedEffect(Unit) {
        rotationState -= 360f
    }


    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            if (searchState) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            searchState = !searchState
                        },
                        modifier = Modifier
                            .width(27.dp)
                            .height(27.dp)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "image",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .width(27.dp)
                                .height(27.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedTextField(
                        value = searchText.value,
                        onValueChange = {
                            searchText.value = it
                        },
                        label = null,
                        placeholder = {
                            Text(
                                stringResource(R.string.search_conversation),
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
                            .heightIn(max = 50.dp)
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
                }

            } else {
                AppBar(
                    onClickAction = {},
                    image = R.drawable.app_icon,
                    text = stringResource(R.string.history),
                    Green,
                    menuItems = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    searchState = !searchState
                                },
                                modifier = Modifier
                                    .width(27.dp)
                                    .height(27.dp)
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.search_vector),
                                    contentDescription = "image",
                                    tint = MaterialTheme.colors.surface,
                                    modifier = Modifier
                                        .width(27.dp)
                                        .height(27.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            IconButton(
                                onClick = {
                                    navigateToDeleteConversations()
                                },
                                modifier = Modifier
                                    .width(27.dp)
                                    .height(27.dp)
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.delete),
                                    contentDescription = "image",
                                    tint = MaterialTheme.colors.surface,
                                    modifier = Modifier
                                        .width(27.dp)
                                        .height(27.dp)
                                )
                            }
                        }

                    }
                )
            }
            if (isFetching) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = null,
                        tint = Green,
                        modifier = Modifier
                            .size(150.dp)
                            .rotate(rotation)
                    )

                }
            } else if (conversations.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = if (darkMode) R.drawable.empty_list_dark else R.drawable.empty_list),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = stringResource(R.string.empty),
                        color = Green,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        modifier = Modifier.padding(top = 50.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_history),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        modifier = Modifier.padding(top = 15.dp)

                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                ) {

                    items(items = conversations.filter {
                        it.title.contains(searchText.value, ignoreCase = true)
                    }, key = { it.id }) { conversation ->

                        val currentItem by rememberUpdatedState(conversation)

                        val dismissState = rememberDismissState()

                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            historyViewModel.deleteConversation(
                                conversation.id
                            )
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = Dp(1f))
                                .animateItemPlacement(),
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),
                            dismissThresholds = { direction ->
                                FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                            },
                            background = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.Default -> MaterialTheme.colors.background
                                        else -> Red
                                    },
                                    animationSpec = tween(100)
                                )
                                val alignment = Alignment.CenterEnd
                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                )

                                Box(
                                    modifier = Modifier
                                        .bounceClick {
                                            navigateToChat(
                                                "",
                                                "",
                                                null,
                                                currentItem.id
                                            )
                                        }
                                        .padding(vertical = 5.dp, horizontal = 5.dp)
                                        .fillMaxSize()
                                        .background(color, RoundedCornerShape(16.dp))
                                        .padding(vertical = 15.dp, horizontal = 15.dp),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.delete_filled),
                                        tint = White,
                                        contentDescription = "Delete Icon",
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            },
                            dismissContent = {

                                Row(
                                    modifier = Modifier
                                        .bounceClick {
                                            navigateToChat(
                                                "",
                                                "",
                                                null,
                                                currentItem.id
                                            )
                                        }
                                        .padding(vertical = 5.dp, horizontal = 5.dp)
                                        .fillMaxWidth()
                                        .background(
                                            MaterialTheme.colors.onSecondary,
                                            RoundedCornerShape(16.dp)
                                        )
                                        .border(
                                            2.dp,
                                            MaterialTheme.colors.onPrimary,
                                            RoundedCornerShape(16.dp)
                                        )
                                        .padding(vertical = 15.dp, horizontal = 15.dp),
                                    verticalAlignment = Alignment.CenterVertically

                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = currentItem.title,
                                            color = MaterialTheme.colors.surface,
                                            maxLines = 1,
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.W700,
                                                fontFamily = Urbanist,
                                                lineHeight = 25.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = currentItem.createdAt.toFormattedDate(),
                                            color = MaterialTheme.colors.onSurface,
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.W600,
                                                fontFamily = Urbanist,
                                                lineHeight = 25.sp
                                            )
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(id = R.drawable.right),
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.surface,
                                        modifier = Modifier
                                            .padding(start = 5.dp)
                                            .size(30.dp)
                                    )


                                }
                            }
                        )


                    }
                }

            }


        }
    }


}
