package jh.subscribingpost

import jh.post.model.Post
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

/*
{
    "_id": "12_2",
    "postId": 12,
    "followerUserId": 2,
    "postCreatedAt": "2023-12-25T13:22:58.070Z",
    "addedAt": "2023-12-25T13:24:50.010Z",
    "read": false
}
 */
@Document(collation = "subscribingInboxPosts")
class SubscribingPostDocument(
    @Id
    val id: String, // postId와 followerUserId의 조합

    val postId: Long,
    val followerUserId: Long,
    val postCreatedAt: LocalDateTime,
    val addedAt: LocalDateTime, // follower 유저의 구독 목록에 반영된 시점
    val read: Boolean
) {
    companion object {
        fun generate(post: Post, followerUserId: Long): SubscribingPostDocument {
            return SubscribingPostDocument(
                generateDocumentId(post.id!!, followerUserId),
                post.id!!,
                followerUserId,
                post.createdAt,
                LocalDateTime.now(),
                false
            )
        }

        private fun generateDocumentId(postId: Long, followerUserId: Long): String {
            return "${postId}_${followerUserId}"
        }
    }
}
