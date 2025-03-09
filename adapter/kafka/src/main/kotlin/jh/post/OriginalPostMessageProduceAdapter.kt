package jh.post

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import jh.OriginalPostMessageProducePort
import jh.common.OperationType
import jh.common.Topic.Companion.ORIGINAL_TOPIC
import jh.post.model.Post
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OriginalPostMessageProduceAdapter(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val objectMapper: ObjectMapper // TODO: CustomObjectMapper
) : OriginalPostMessageProducePort {

    override fun sendCreateMessage(post: Post) {
        val message = this.convertToMessage(post.id, post, OperationType.CREATE)

        this.sendMessage(message)
    }

    override fun sendUpdateMessage(post: Post) {
        val message = this.convertToMessage(post.id, post, OperationType.UPDATE)

        this.sendMessage(message)
    }

    override fun sendDeleteMessage(id: Long) {
        val message = this.convertToMessage(id, null, OperationType.DELETE)

        this.sendMessage(message)
    }

    private fun convertToMessage(id: Long?, post: Post?, operationType: OperationType): OriginalPostMessage {
        return OriginalPostMessage(
            id = id!!,
            payload = post?.let {
                OriginalPostMessage.Payload(
                    it.id,
                    it.title,
                    it.content,
                    it.userId,
                    it.categoryId,
                    it.createdAt,
                    it.updatedAt,
                    it.deletedAt
                )
            },
            operationType = operationType
        )
    }

    private fun sendMessage(message: OriginalPostMessage) {
        try {
            kafkaTemplate.send(ORIGINAL_TOPIC, message.id.toString(), objectMapper.writeValueAsString(message))
            println("Message sent successfully: ${objectMapper.writeValueAsString(message)}")
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}

