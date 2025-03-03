package jh.post.model

import java.time.LocalDateTime

/**
 * 원천 데이터 관리용
 */
class Post(
    var id: Long?,
    var title: String,
    var content: String,
    var userId: Long,
    var categoryId: Long,

    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var deletedAt: LocalDateTime?,
) {

    fun update(title: String, content: String, categoryId: Long): Post {
        this.title = title
        this.content = content
        this.categoryId = categoryId
        this.updatedAt = LocalDateTime.now()

        return this
    }

    fun delete(): Post {
        val now = LocalDateTime.now()

        this.deletedAt = now
        this.updatedAt = now

        return this
    }

    fun undelete(): Post {
        this.deletedAt = null

        return this
    }

    companion object {
        fun generate(userId: Long, title: String, content: String, categoryId: Long): Post {
            val now = LocalDateTime.now()

            return Post(null, title, content, userId, categoryId, now, now, null)
        }
    }
}
