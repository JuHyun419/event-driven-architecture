package jh.chatgpt

import jh.TestChatGptPort
import org.springframework.stereotype.Component

@Component
class TestChatGptAdapter(
    val chatGptClient: ChatGptClient
) : TestChatGptPort {

    override fun test(content: String): String {
        return chatGptClient.testChatGpt(content)
    }
}
