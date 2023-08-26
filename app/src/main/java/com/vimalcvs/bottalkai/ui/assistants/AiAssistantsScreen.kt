package com.vimalcvs.bottalkai.ui.assistants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vimalcvs.bottalkai.R
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.common.components.AppBar
import com.vimalcvs.bottalkai.common.components.AssistantCard
import com.vimalcvs.bottalkai.common.components.ChipItem
import com.vimalcvs.bottalkai.data.model.AiAssistantModel
import com.vimalcvs.bottalkai.data.model.AiAssistantsModel
import com.vimalcvs.bottalkai.ui.theme.Green
import com.vimalcvs.bottalkai.ui.theme.PastelAqua
import com.vimalcvs.bottalkai.ui.theme.PastelBlue
import com.vimalcvs.bottalkai.ui.theme.PastelCoral
import com.vimalcvs.bottalkai.ui.theme.PastelGreen
import com.vimalcvs.bottalkai.ui.theme.PastelLavender
import com.vimalcvs.bottalkai.ui.theme.PastelLilac
import com.vimalcvs.bottalkai.ui.theme.PastelOrange
import com.vimalcvs.bottalkai.ui.theme.PastelPink
import com.vimalcvs.bottalkai.ui.theme.PastelPurple
import com.vimalcvs.bottalkai.ui.theme.PastelRed
import com.vimalcvs.bottalkai.ui.theme.PastelTeal
import com.vimalcvs.bottalkai.ui.theme.PastelYellow
import com.vimalcvs.bottalkai.ui.theme.Urbanist

@Composable
fun AiAssistantsScreen(
    navigateToChat: (String, String, List<String>) -> Unit,
    viewModel: AiAssistantsViewModel = viewModel()
) {

    val assistantList: List<AiAssistantsModel> = listOf(
        AiAssistantsModel(
            title = stringResource(R.string.writing), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.memo,
                    color = PastelGreen,
                    name = stringResource(R.string.write_article),
                    description = stringResource(R.string.write_article_description),
                    role = Constants.Writing.WRITE_ARTICLE,
                    example = listOf(
                        stringResource(R.string.write_article_example1),
                        stringResource(R.string.write_article_example2),
                        stringResource(R.string.write_article_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.cap,
                    color = PastelBlue,
                    name = stringResource(R.string.academic_writer),
                    description = stringResource(R.string.academic_writer_description),
                    role = Constants.Writing.ACADEMIC_WRITER,
                    example = listOf(
                        stringResource(R.string.academic_writer_example1),
                        stringResource(R.string.academic_writer_example2),
                        stringResource(R.string.academic_writer_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.page_facing_up,
                    color = PastelRed,
                    name = stringResource(R.string.summarize),
                    description = stringResource(R.string.summarize_description),
                    role = Constants.Writing.SUMMARIZE,
                    example = listOf(
                        stringResource(R.string.summarize_example1),
                        stringResource(R.string.summarize_example2),
                        stringResource(R.string.summarize_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.earth,
                    color = PastelOrange,
                    name = stringResource(R.string.translate_language),
                    description = stringResource(R.string.translate_language_description),
                    role = Constants.Writing.TRANSLATE_LANGUAGE,
                    example = listOf(
                        stringResource(R.string.translate_language_example1),
                        stringResource(R.string.translate_language_example2),
                        stringResource(R.string.translate_language_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.search,
                    color = PastelPink,
                    name = stringResource(R.string.plagiarism_checker),
                    description = stringResource(R.string.plagiarism_checker_description),
                    role = Constants.Writing.PLAGIARISM_CHECKER,
                    example = listOf(
                        stringResource(R.string.plagiarism_checker_example1),
                        stringResource(R.string.plagiarism_checker_example2),
                        stringResource(R.string.plagiarism_checker_example3)
                    )
                ),
            )
        ),
        AiAssistantsModel(
            title = stringResource(R.string.creative), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.musical,
                    color = PastelYellow,
                    name = stringResource(R.string.song_lyrics),
                    description = stringResource(R.string.song_lyrics_description),
                    role = Constants.Creative.SONG_LYRICS,
                    example = listOf(
                        stringResource(R.string.song_lyrics_example1),
                        stringResource(R.string.song_lyrics_example2),
                        stringResource(R.string.song_lyrics_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.open_book,
                    color = PastelAqua,
                    name = stringResource(R.string.storyteller),
                    description = stringResource(R.string.storyteller_description),
                    role = Constants.Creative.STORYTELLER,
                    example = listOf(
                        stringResource(R.string.storyteller_example1),
                        stringResource(R.string.storyteller_example2),
                        stringResource(R.string.storyteller_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.scroll,
                    color = PastelGreen,
                    name = stringResource(R.string.poems),
                    description = stringResource(R.string.poems_description),
                    role = Constants.Creative.POEMS,
                    example = listOf(
                        stringResource(R.string.poems_example1),
                        stringResource(R.string.poems_example2),
                        stringResource(R.string.poems_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.clapper,
                    color = PastelPink,
                    name = stringResource(R.string.movie_script),
                    description = stringResource(R.string.movie_script_description),
                    role = Constants.Creative.MOVIE_SCRIPT,
                    example = listOf(
                        stringResource(R.string.movie_script_example1),
                        stringResource(R.string.movie_script_example2),
                        stringResource(R.string.movie_script_example3)
                    )
                )
            )
        ),
        AiAssistantsModel(
            title = stringResource(R.string.business), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.envelope,
                    color = PastelPurple,
                    name = stringResource(R.string.email_writer),
                    description = stringResource(R.string.email_writer_description),
                    role = Constants.Business.EMAIL_WRITER,
                    example = listOf(
                        stringResource(R.string.email_writer_example1),
                        stringResource(R.string.email_writer_example2),
                        stringResource(R.string.email_writer_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.page_with_curl,
                    color = PastelOrange,
                    name = stringResource(R.string.answer_interviewer),
                    description = stringResource(R.string.answer_interviewer_description),
                    role = Constants.Business.ANSWER_INTERVIEWER,
                    example = listOf(
                        stringResource(R.string.answer_interviewer_example1),
                        stringResource(R.string.answer_interviewer_example2),
                        stringResource(R.string.answer_interviewer_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.briefcase,
                    color = PastelLilac,
                    name = stringResource(R.string.job_post),
                    description = stringResource(R.string.job_post_description),
                    role = Constants.Business.JOB_POST,
                    example = listOf(
                        stringResource(R.string.job_post_example1),
                        stringResource(R.string.job_post_example2),
                        stringResource(R.string.job_post_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.star,
                    color = PastelAqua,
                    name = stringResource(R.string.advertisement),
                    description = stringResource(R.string.advertisement_description),
                    role = Constants.Business.ADVERTISEMENT,
                    example = listOf(
                        stringResource(R.string.advertisement_example1),
                        stringResource(R.string.advertisement_example2),
                        stringResource(R.string.advertisement_example3)
                    )
                )
            )
        ),
        AiAssistantsModel(
            title = stringResource(R.string.social_media), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.linkedin,
                    color = PastelAqua,
                    name = stringResource(R.string.linkedin),
                    description = stringResource(R.string.linkedin_description),
                    role = Constants.SocialMedia.LINKEDIN,
                    example = listOf(
                        stringResource(R.string.linkedin_example1),
                        stringResource(R.string.linkedin_example2),
                        stringResource(R.string.linkedin_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.instagram,
                    color = PastelYellow,
                    name = stringResource(R.string.instagram),
                    description = stringResource(R.string.instagram_description),
                    role = Constants.SocialMedia.INSTAGRAM,
                    example = listOf(
                        stringResource(R.string.instagram_example1),
                        stringResource(R.string.instagram_example2),
                        stringResource(R.string.instagram_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.twitter,
                    color = PastelBlue,
                    name = stringResource(R.string.twitter),
                    description = stringResource(R.string.twitter_description),
                    role = Constants.SocialMedia.TWITTER,
                    example = listOf(
                        stringResource(R.string.twitter_example1),
                        stringResource(R.string.twitter_example2),
                        stringResource(R.string.twitter_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.tiktok,
                    color = PastelTeal,
                    name = stringResource(R.string.tiktok),
                    description = stringResource(R.string.tiktok_description),
                    role = Constants.SocialMedia.TIKTOK,
                    example = listOf(
                        stringResource(R.string.tiktok_example1),
                        stringResource(R.string.tiktok_example2),
                        stringResource(R.string.tiktok_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.facebook,
                    color = PastelLavender,
                    name = stringResource(R.string.facebook),
                    description = stringResource(R.string.facebook_description),
                    role = Constants.SocialMedia.FACEBOOK,
                    example = listOf(
                        stringResource(R.string.facebook_example1),
                        stringResource(R.string.facebook_example2),
                        stringResource(R.string.facebook_example3)
                    )
                )
            )
        ),
        AiAssistantsModel(
            title = stringResource(R.string.developer), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.laptop,
                    color = PastelGreen,
                    name = stringResource(R.string.write_code),
                    description = stringResource(R.string.write_code_description),
                    role = Constants.Developer.WRITE_CODE,
                    example = listOf(
                        stringResource(R.string.write_code_example1),
                        stringResource(R.string.write_code_example2),
                        stringResource(R.string.write_code_example3)
                    )

                ),
                AiAssistantModel(
                    image = R.drawable.puzzle,
                    color = PastelRed,
                    name = stringResource(R.string.explain_code),
                    description = stringResource(R.string.explain_code_description),
                    role = Constants.Developer.EXPLAIN_CODE,
                    example = listOf(
                        stringResource(R.string.explain_code_example1),
                        stringResource(R.string.explain_code_example2),
                        stringResource(R.string.explain_code_example3)
                    )
                )
            )
        ),
        AiAssistantsModel(
            title = stringResource(R.string.personal), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.cake,
                    color = PastelYellow,
                    name = stringResource(R.string.birtday),
                    description = stringResource(R.string.birtday_description),
                    role = Constants.Personal.BIRTHDAY,
                    example = listOf(
                        stringResource(R.string.birtday_example1),
                        stringResource(R.string.birtday_example2),
                        stringResource(R.string.birtday_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.gift,
                    color = PastelCoral,
                    name = stringResource(R.string.apology),
                    description = stringResource(R.string.apology_description),
                    role = Constants.Personal.APOLOGY,
                    example = listOf(
                        stringResource(R.string.apology_example1),
                        stringResource(R.string.apology_example2),
                        stringResource(R.string.apology_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.envelope_arrow,
                    color = PastelBlue,
                    name = stringResource(R.string.invitation),
                    description = stringResource(R.string.invitation_description),
                    role = Constants.Personal.INVITATION,
                    example = listOf(
                        stringResource(R.string.invitation_example1),
                        stringResource(R.string.invitation_example2),
                        stringResource(R.string.invitation_example3)
                    )
                )
            )
        ),
        AiAssistantsModel(
            title = stringResource(R.string.other), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.balloon,
                    color = PastelPink,
                    name = stringResource(R.string.create_conversation),
                    description = stringResource(R.string.create_conversation_description),
                    role = Constants.Other.CREATE_CONVERSATION,
                    example = listOf(
                        stringResource(R.string.create_conversation_example1),
                        stringResource(R.string.create_conversation_example2),
                        stringResource(R.string.create_conversation_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.laugh,
                    color = PastelLavender,
                    name = stringResource(R.string.tell_joke),
                    description = stringResource(R.string.tell_joke_description),
                    role = Constants.Other.TELL_JOKE,
                    example = listOf(
                        stringResource(R.string.tell_joke_example1),
                        stringResource(R.string.tell_joke_example2),
                        stringResource(R.string.tell_joke_example3)
                    )
                ),
                AiAssistantModel(
                    image = R.drawable.food,
                    color = PastelRed,
                    name = stringResource(R.string.food_recipes),
                    description = stringResource(R.string.food_recipes_description),
                    role = Constants.Other.FOOD_RECIPES,
                    example = listOf(
                        stringResource(R.string.food_recipes_example1),
                        stringResource(R.string.food_recipes_example2),
                        stringResource(R.string.food_recipes_example3)
                    )
                ), AiAssistantModel(
                    image = R.drawable.leafy,
                    color = PastelGreen,
                    name = stringResource(R.string.diet_plan),
                    description = stringResource(R.string.diet_plan_description),
                    role = Constants.Other.DIET_PLAN,
                    example = listOf(
                        stringResource(R.string.diet_plan_example1),
                        stringResource(R.string.diet_plan_example2),
                        stringResource(R.string.diet_plan_example3)
                    )
                )
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            AppBar(
                onClickAction = {},
                image = R.drawable.app_icon,
                text = stringResource(R.string.ai_assistants),
                Green
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp)
            )
            {
                item {
                    ChipItem(
                        text = stringResource(R.string.all),
                        selected = viewModel.selectedValue.value == "",
                        onClick = {
                            viewModel.selectedValue.value = ""
                            viewModel.showVertical.value = false
                        }
                    )
                }
                items(assistantList) {
                    ChipItem(
                        text = it.title,
                        selected = viewModel.selectedValue.value == it.title,
                        onClick = {
                            viewModel.selectedValue.value = it.title

                            viewModel.verticalShowList.clear()
                            viewModel.verticalShowList.addAll(it.assistant)
                            viewModel.showVertical.value = true

                        }
                    )
                }
            }

            if (viewModel.showVertical.value) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                ) {
                    items(viewModel.verticalShowList)
                    {
                        AssistantCard(
                            image = it.image,
                            color = it.color,
                            name = it.name,
                            description = it.description,
                            onClick = { navigateToChat(it.name, it.role, it.example) }
                        )

                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    items(assistantList)
                    { aiAssistantList ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, top = 15.dp)
                        ) {
                            Text(
                                text = aiAssistantList.title,
                                color = MaterialTheme.colors.surface,
                                style = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = Urbanist,
                                    lineHeight = 25.sp
                                ), modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = {
                                    viewModel.selectedValue.value = aiAssistantList.title
                                    viewModel.verticalShowList.clear()
                                    viewModel.verticalShowList.addAll(aiAssistantList.assistant)
                                    viewModel.showVertical.value = true
                                },
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(30.dp)
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_right),
                                    contentDescription = null,
                                    tint = Green
                                )
                            }


                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(aiAssistantList.assistant) {
                                AssistantCard(
                                    image = it.image,
                                    color = it.color,
                                    name = it.name,
                                    description = it.description,
                                    onClick = {
                                        navigateToChat(
                                            it.name,
                                            it.role,
                                            it.example
                                        )
                                    }
                                )

                            }

                        }
                    }
                }

            }


        }
    }

}
