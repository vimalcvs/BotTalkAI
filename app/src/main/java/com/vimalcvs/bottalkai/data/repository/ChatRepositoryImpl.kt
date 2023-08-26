package com.vimalcvs.bottalkai.data.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vimalcvs.bottalkai.common.Constants
import com.vimalcvs.bottalkai.data.model.TextCompletionsParam
import com.vimalcvs.bottalkai.data.model.toJson
import com.vimalcvs.bottalkai.data.source.remote.BotTalkAIService
import com.vimalcvs.bottalkai.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val BotTalkAIService: BotTalkAIService) :
    ChatRepository {
    override fun textCompletionsWithStream(
        scope: CoroutineScope,
        params: TextCompletionsParam
    ): Flow<String> =
        callbackFlow {
            withContext(Dispatchers.IO) {

                val response = (if (params.isTurbo) BotTalkAIService.textCompletionsTurboWithStream(
                    params.toJson()
                ) else BotTalkAIService.textCompletionsWithStream(params.toJson())).execute()

                if (response.isSuccessful) {
                    val input = response.body()?.byteStream()?.bufferedReader() ?: throw Exception()
                    try {
                        while (true) {
                            val line = withContext(Dispatchers.IO) {
                                input.readLine()
                            } ?: continue
                            if (line == "data: [DONE]") {
                                close()
                            } else if (line.startsWith("data:")) {
                                try {
                                    // Handle & convert data -> emit to client
                                    val value =
                                        if (params.isTurbo) lookupDataFromResponseTurbo(line) else lookupDataFromResponse(
                                            line
                                        )

                                    if (value.isNotEmpty()) {
                                        trySend(value)
                                    }
                                } catch (e: Exception) {

                                    e.printStackTrace()
                                }
                            }
                            if (!scope.isActive) {
                                break
                            }
                        }
                    } catch (e: IOException) {
                        throw Exception(e)
                    } finally {
                        withContext(Dispatchers.IO) {
                            input.close()
                        }

                        close()
                    }
                } else {
                    if (!response.isSuccessful) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            println(jsonObject)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    trySend("Failure! Try again.")
                    close()
                }
            }

            close()
        }.flowOn(Dispatchers.IO)

    private fun lookupDataFromResponse(jsonString: String): String {
        val splitsJsonString = jsonString.split("[{")

        val indexOfResult: Int = splitsJsonString.indexOfLast {
            it.contains(Constants.MATCH_RESULT_STRING)
        }

        val textSplits =
            if (indexOfResult == -1) listOf() else splitsJsonString[indexOfResult].split(",")

        val indexOfText: Int = textSplits.indexOfLast {
            it.contains(Constants.MATCH_RESULT_STRING)
        }

        if (indexOfText != -1) {
            try {
                val gson = Gson()
                val jsonObject =
                    gson.fromJson("{${textSplits[indexOfText]}}", JsonObject::class.java)

                return jsonObject.get("text").asString
            } catch (e: java.lang.Exception) {
                println(e.localizedMessage)
            }
        }

        return ""
    }

    private fun lookupDataFromResponseTurbo(jsonString: String): String {
        val splitsJsonString = jsonString.split("[{")

        val indexOfResult: Int = splitsJsonString.indexOfLast {
            it.contains(Constants.MATCH_RESULT_TURBO_STRING)
        }

        val textSplits =
            if (indexOfResult == -1) listOf() else splitsJsonString[indexOfResult].split(",")

        val indexOfText: Int = textSplits.indexOfLast {
            it.contains(Constants.MATCH_RESULT_TURBO_STRING)
        }

        if (indexOfText != -1) {
            try {
                val gson = Gson()
                val jsonObject =
                    gson.fromJson("{${textSplits[indexOfText]}}", JsonObject::class.java)

                return jsonObject.getAsJsonObject("delta").get("content").asString
            } catch (e: java.lang.Exception) {
                println(e.localizedMessage)
            }
        }

        return ""
    }
}