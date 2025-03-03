package jh.dto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val categoryId: Long,
)
