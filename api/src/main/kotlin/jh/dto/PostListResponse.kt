package jh.dto

import java.time.LocalDateTime

data class PostListResponse(
    val id: Long,
    val title: String,
    val userName: String,
    val createdAt: LocalDateTime
)
