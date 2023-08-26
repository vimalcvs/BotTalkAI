package com.vimalcvs.bottalkai.data.source.remote

import com.google.gson.JsonObject
import com.vimalcvs.bottalkai.common.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface BotTalkAIService {

    @POST(Constants.Endpoints.TEXT_COMPLETIONS)
    @Streaming
    fun textCompletionsWithStream(@Body body: JsonObject): Call<ResponseBody>

    @POST(Constants.Endpoints.TEXT_COMPLETIONS_TURBO)
    @Streaming
    fun textCompletionsTurboWithStream(@Body body: JsonObject): Call<ResponseBody>
}