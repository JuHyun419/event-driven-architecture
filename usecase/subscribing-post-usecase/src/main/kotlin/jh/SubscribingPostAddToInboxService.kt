package jh

import jh.post.model.Post
import org.springframework.stereotype.Service

@Service
class SubscribingPostAddToInboxService(
    private val subscribingPostPort: SubscribingPostPort,
    private val metadataPort: MetadataPort,
) : SubscribingPostAddToInboxUseCase {

    override fun saveSubscribingInboxPost(post: Post) {
        val followerUserIds = metadataPort.listFollowerIdsByUserId(post.userId)

        subscribingPostPort.addPostToFollowerInboxes(post, followerUserIds)
    }
}
