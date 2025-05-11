package jh

import jh.inspected.InspectedPost

interface PostIndexingUseCase {

    fun save(post: InspectedPost)

    fun delete(postId: Long)
}
