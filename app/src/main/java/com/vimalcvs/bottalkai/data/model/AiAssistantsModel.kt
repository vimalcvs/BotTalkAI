package com.vimalcvs.bottalkai.data.model

import androidx.compose.ui.graphics.Color

data class AiAssistantsModel(
    var title: String = "",
    var assistant: List<AiAssistantModel>,
)


data class AiAssistantModel(
    var image: Int,
    var color: Color,
    var name: String = "",
    var description: String = "",
    var role: String = "",
    val example: List<String>
)
