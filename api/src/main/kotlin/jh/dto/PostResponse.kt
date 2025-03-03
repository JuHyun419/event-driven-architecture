package jh.dto

import java.time.LocalDateTime

class PostResponse(
    var id: Long?,
    var title: String,
    var content: String,
    var userId: Long,
    var categoryId: Long,

    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var deletedAt: LocalDateTime?,
) {
}
