package jh.post.model

import java.time.LocalDateTime

/**
 * 서비스에서 사용하기 위한 모델
 */
class ResolvedPost(
    var id: Long,
    var title: String,
    var content: String,
    var userId: Long,
    var userName: String,
    var categoryId: Long,
    var categoryName: String,

    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var updated: Boolean
) {

    companion object {
        fun generate(post: Post, userName: String, categoryName: String): ResolvedPost {
            return ResolvedPost(
                post.id!!,
                post.title,
                post.content,
                post.userId,
                userName,
                post.categoryId,
                categoryName,
                post.createdAt,
                post.updatedAt,
                post.createdAt != post.updatedAt
            )
        }
    }
}
