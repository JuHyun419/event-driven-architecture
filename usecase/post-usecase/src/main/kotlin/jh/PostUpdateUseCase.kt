package jh

import jh.post.model.Post

interface PostUpdateUseCase {

    fun update(request: UpdateRequest): Post?

    data class UpdateRequest(
        val postId: Long,
        val title: String,
        val content: String,
        val categoryId: Long,
    )
}
