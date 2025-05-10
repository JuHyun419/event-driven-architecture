package jh.consumer

import OriginalPostMessageConverter
import com.fasterxml.jackson.databind.ObjectMapper
import jh.PostResolvingHelperUseCase
import jh.common.OperationType
import jh.common.Topic
import jh.originalpost.OriginalPostMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentCachingWorker(
    private val objectMapper: ObjectMapper,
    private val postResolvingHelperUseCase: PostResolvingHelperUseCase,
) {

    @KafkaListener(
        topics = [Topic.ORIGINAL_TOPIC],
        groupId = "cache-post-consumer-group",
        concurrency = "3"
    )
    fun consumer(message: ConsumerRecord<String, String>) {
        val originalPostMessage = objectMapper.readValue(message.value(), OriginalPostMessage::class.java)

        when (originalPostMessage.operationType) {
            OperationType.CREATE -> { handleCreate(originalPostMessage) }
            OperationType.UPDATE -> { handleUpdate(originalPostMessage) }
            OperationType.DELETE -> { handleDelete(originalPostMessage) }
        }
    }

    private fun handleCreate(originalPostMessage: OriginalPostMessage) {
        postResolvingHelperUseCase.resolvePostAndSave(OriginalPostMessageConverter.toModel(originalPostMessage))
    }

    private fun handleUpdate(originalPostMessage: OriginalPostMessage) {
        postResolvingHelperUseCase.resolvePostAndSave(OriginalPostMessageConverter.toModel(originalPostMessage))
    }

    private fun handleDelete(originalPostMessage: OriginalPostMessage) {
        postResolvingHelperUseCase.deleteResolvedPost(originalPostMessage.id)
    }
}
