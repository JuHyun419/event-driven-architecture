package jh.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import jh.PostIndexingUseCase
import jh.common.OperationType
import jh.common.Topic
import jh.inspectedpost.InspectPostMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentIndexingWorker(
    private val objectMapper: ObjectMapper,
    private val postIndexingUseCase: PostIndexingUseCase,
) {

    @KafkaListener(
        topics = [Topic.INSPECTED_TOPIC],
        groupId = "indexing-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val inspectedPostMessage = objectMapper.readValue(message.value(), InspectPostMessage::class.java)

        when (inspectedPostMessage.operationType) {
            OperationType.CREATE -> { handleCreate(inspectedPostMessage) }
            OperationType.UPDATE -> { TODO() }
            OperationType.DELETE -> { handleDelete(inspectedPostMessage) }
        }
    }

    private fun handleCreate(inspectedPostMessage: InspectPostMessage) {
        postIndexingUseCase.save(inspectedPostMessage.toModel())
    }

    private fun handleDelete(inspectedPostMessage: InspectPostMessage) {
        postIndexingUseCase.delete(inspectedPostMessage.id)
    }
}
