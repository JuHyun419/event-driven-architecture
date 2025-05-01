package jh

import org.springframework.stereotype.Service

@Service
class SubscribingPostRemoveFromInboxService(
    private val subscribingPostPort: SubscribingPostPort
) : SubscribingPostRemoveFromInboxUseCase {

    override fun deleteSubscribingInboxPost(postId: Long) {
        subscribingPostPort.removePostFromFollowerInboxes(postId)
    }
}
