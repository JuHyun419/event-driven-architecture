package jh.originalpost

import jh.common.OperationType
import java.time.LocalDateTime

data class OriginalPostMessage(
    val id: Long = 0L,
    val payload: Payload? = null,
    val operationType: OperationType = OperationType.CREATE,
) {

    // 원천 데이터 관리용 Post 와 동일한 필드
    data class Payload(
        val id: Long? = null,
        val title: String = "",
        val content: String = "",
        val userId: Long = 0L,
        val categoryId: Long = 0L,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        val updatedAt: LocalDateTime = LocalDateTime.now(),
        val deletedAt: LocalDateTime? = null
    )
}
