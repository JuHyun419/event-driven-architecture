package jh

import jh.post.model.Post

interface PostCreateUseCase {

    fun create(request: CreateRequest): Post

    data class CreateRequest(
        val userId: Long,
        val title: String,
        val content: String,
        val categoryId: Long,
    )

}
