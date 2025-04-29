import jh.originalpost.OriginalPostMessage
import jh.post.model.Post

object OriginalPostMessageConverter {
    fun toModel(originalPostMessage: OriginalPostMessage): Post {
        return Post(
            id = originalPostMessage.payload?.id,
            title = originalPostMessage.payload!!.title,
            content = originalPostMessage.payload.content,
            userId = originalPostMessage.payload.userId,
            categoryId = originalPostMessage.payload.categoryId,
            createdAt = originalPostMessage.payload.createdAt,
            updatedAt = originalPostMessage.payload.updatedAt,
            deletedAt = originalPostMessage.payload.deletedAt
        )
    }
}
