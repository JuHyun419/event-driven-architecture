package jh.consumer

import OriginalPostMessageConverter
import com.fasterxml.jackson.databind.ObjectMapper
import jh.InspectedPostMessageProducePort
import jh.PostInspectUseCase
import jh.common.OperationType
import jh.common.Topic
import jh.originalpost.OriginalPostMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class AutoInspectionWorker(
    private val postInspectUseCase: PostInspectUseCase,
    private val inspectedPostMessageProducePort: InspectedPostMessageProducePort,

    private val objectMapper: ObjectMapper
) {

    @KafkaListener(
        topics = [Topic.ORIGINAL_TOPIC],
        groupId = "auto-inspection-consumer-group",
        concurrency = "3"
    )
    fun listener(message: ConsumerRecord<String, String>) {
        print("AutoInspectionWorker::@KafkaListener ${message.key()}, ${message.value()}")
        val originalPostMessage = objectMapper.readValue(message.value(), OriginalPostMessage::class.java)

        when (originalPostMessage.operationType) {
            OperationType.CREATE -> { handleCreate(originalPostMessage) }
            OperationType.UPDATE -> { handleUpdate(originalPostMessage) }
            OperationType.DELETE -> { handleDelete(originalPostMessage) }
        }
    }

    private fun handleCreate(originalPostMessage: OriginalPostMessage) {
        val inspectedPost = postInspectUseCase.inspectAndGetIfValid(
            OriginalPostMessageConverter.toModel(originalPostMessage)
        )

        inspectedPost?.let {
            inspectedPostMessageProducePort.sendCreateMessage(inspectedPost)
        }
    }

    private fun handleUpdate(originalPostMessage: OriginalPostMessage) {
        val inspectedPost = postInspectUseCase.inspectAndGetIfValid(
            OriginalPostMessageConverter.toModel(originalPostMessage)
        )

        if (inspectedPost == null) {
            inspectedPostMessageProducePort.sendDeleteMessage(originalPostMessage.id)
        } else {
            inspectedPostMessageProducePort.sendUpdateMessage(inspectedPost)
        }
    }

    private fun handleDelete(originalPostMessage: OriginalPostMessage) {
        inspectedPostMessageProducePort.sendDeleteMessage(originalPostMessage.id)
    }
}
