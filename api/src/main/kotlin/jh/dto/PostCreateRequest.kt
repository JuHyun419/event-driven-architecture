package jh.dto

data class PostCreateRequest(
    val title: String,
    val userId: Long,
    val content: String,
    val categoryId: Long,
)
