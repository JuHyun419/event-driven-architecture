package jh.chatgpt

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jh.PostAutoInspectPort
import jh.inspected.AutoInspectionResult
import jh.post.model.Post
import org.springframework.stereotype.Component

@Component
class PostAutoInspectAdapter(
    val chatGptClient: ChatGptClient,
    val objectMapper: ObjectMapper
) : PostAutoInspectPort {

    val logger = KotlinLogging.logger { }

    override fun inspect(post: Post, categoryName: String): AutoInspectionResult {
        // ChatGPT 에게 할 말을 적는다.
        val contentString = buildContentString(post, categoryName)

        // ChatGPT 에게 어떻게 대답을 해야할지 지침을 준다.
        val chatPolicy = ChatGptClient.ChatPolicy(
            AutoInspectionPolicy.INSPECTION_INSTRUCTION,
            AutoInspectionPolicy.EXAMPLE_CONTENT,
            AutoInspectionPolicy.EXAMPLE_INSPECTION_RESULT,
        )

        // ChatGPT 에게 검수를 시켜서 결과를 얻는다.
        val contentInspectionResult = chatGptClient.getContentInspectionResult(contentString, chatPolicy)

        logger.info { "Post[`${post.title} - ${post.content}`]\nInspection Result: $contentInspectionResult" }

        return objectMapper.readValue(contentInspectionResult, AutoInspectionResult::class.java)
    }

    private fun buildContentString(post: Post, categoryName: String): String {
        return "[${categoryName}] ${post.title} - ${post.content}"
    }

    class AutoInspectionPolicy private constructor() {
        companion object {
            const val INSPECTION_INSTRUCTION =
                "The task you need to accomplish is to return two items ('status' and 'tags') in JSON format. " +
                        "The information I will provide will be in the format '[Post category] Post content.' " +
                        "Then, if the content of the post aligns well with the meaning or theme of the post category, " +
                        "fill the 'status' field with the string 'GOOD.' " +
                        "If the meaning or theme appears unrelated, " +
                        "fill the 'status' field with the string 'BAD.' " +
                        "Additionally, extract and compile a list of up to 5 keywords " +
                        "that seem most important in the post content and populate the 'tags' field with them.";

            const val EXAMPLE_CONTENT =
                "[Health] Reps and Muscle Size - To increase muscle size, " +
                        "it is considered most ideal to exercise with the maximum weight " +
                        "that allows 8 to 12 repetitions per set.";

            const val EXAMPLE_INSPECTION_RESULT =
                "{\"status\":\"GOOD\",\"tags\":[\"muscle\", \"weight\", \"repetitions\"]}";
        }
    }
}
