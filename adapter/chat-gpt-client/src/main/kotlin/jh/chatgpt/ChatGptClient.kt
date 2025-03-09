package jh.chatgpt

import com.fasterxml.jackson.databind.ObjectMapper
import jh.chatgpt.model.ChatGptCompletionResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ChatGptClient(
    @Qualifier("chatGptWebClient")
    private val chatGptWebClient: WebClient,
    private val objectMapper: ObjectMapper,

    @Value("\${OPENAI_API_KEY}")
    private val openaiApiKey: String,
) {

    // https://platform.openai.com/docs/api-reference/chat/create
    fun testChatGpt(content: String): String {
        println("openaiApiKey: $openaiApiKey ::::::::::::::::::::")

        val jsonString = chatGptWebClient
            .post()
            .uri("/v1/chat/completions")
            .header("Authorization", "Bearer $openaiApiKey")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                mapOf(
                    "model" to TARGET_GPT_MODEL,
                    "messages" to listOf(
                        mapOf("role" to "system", "content" to "You are an assistant."),
                        mapOf("role" to "user", "content" to content)
                    ),
                    "stream" to false
                )
            )
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        val response = objectMapper.readValue(jsonString, ChatGptCompletionResponse::class.java)

        return response.choices[0].message.content
    }

    companion object {
        // https://platform.openai.com/docs/models/gpt-4o-mini
        private const val TARGET_GPT_MODEL = "gpt-4o-mini"
    }
}
