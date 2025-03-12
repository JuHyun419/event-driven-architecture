package jh.originalpost

import jh.common.OperationType
import java.time.LocalDateTime

data class OriginalPostMessage(
    val id: Long,
    val payload: Payload?,
    val operationType: OperationType,
) {

    // 원천 데이터 관리용 Post 와 동일한 필드
    data class Payload(
        val id: Long?,
        val title: String,
        val content: String,
        val userId: Long,
        val categoryId: Long,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val deletedAt: LocalDateTime?
    )
}
