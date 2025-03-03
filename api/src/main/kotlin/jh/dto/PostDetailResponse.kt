package jh.dto

import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val userName: String,
    val categoryName: String,
    val createdAt: LocalDateTime,
    val updated: Boolean
)
