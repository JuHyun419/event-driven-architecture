package jh

import jh.post.model.ResolvedPost

interface PostReadUseCase {

    fun getById(id: Long): ResolvedPost

}
