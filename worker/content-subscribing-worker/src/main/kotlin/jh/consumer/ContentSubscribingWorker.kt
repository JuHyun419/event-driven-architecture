package jh.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import jh.SubscribingPostAddToInboxUseCase
import jh.SubscribingPostRemoveFromInboxUseCase
import jh.common.OperationType
import jh.common.Topic
import jh.inspectedpost.InspectPostMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ContentSubscribingWorker(
    private val objectMapper: ObjectMapper,
    private val subscribingPostAddToInboxUseCase: SubscribingPostAddToInboxUseCase,
    private val subscribingPostRemoveFromInboxUseCase: SubscribingPostRemoveFromInboxUseCase,
) {

    @KafkaListener(
        topics = [Topic.INSPECTED_TOPIC],
        groupId = "subscribing-post-consumer-group",
        concurrency = "3"
    )
    fun listen(message: ConsumerRecord<String, String>) {
        val inspectedPostMessage = objectMapper.readValue(message.value(), InspectPostMessage::class.java)

        when (inspectedPostMessage.operationType) {
            OperationType.CREATE -> {
                handleCreate(inspectedPostMessage)
            }
            OperationType.UPDATE -> {
                TODO()
            }
            OperationType.DELETE -> {
                handleDelete(inspectedPostMessage)
            }
        }
    }

    private fun handleCreate(inspectedPostMessage: InspectPostMessage) {
        subscribingPostAddToInboxUseCase.saveSubscribingInboxPost(inspectedPostMessage.payload!!.post)
    }

    private fun handleDelete(inspectedPostMessage: InspectPostMessage) {
        subscribingPostRemoveFromInboxUseCase.deleteSubscribingInboxPost(inspectedPostMessage.id)
    }
}
