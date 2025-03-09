package jh.chatgpt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatGptCompletionResponse(
    val id: String,

    @JsonProperty("object")
    val obj: String,
    val created: Long,
    val model: String,
    val choices: List<ChatChoice>,
    val usage: Usage,
    val systemFingerprint: String?
) {
    data class ChatChoice(
        val index: Int,
        val message: Message,
        val logprobs: Any?,
        val finishReason: String?
    ) {
        data class Message(
            val role: String,
            val content: String
        )
    }

    data class Usage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int,
    )
}
