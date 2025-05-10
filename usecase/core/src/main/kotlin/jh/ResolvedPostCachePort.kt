package jh

import jh.post.model.ResolvedPost

interface ResolvedPostCachePort {

    fun get(postId: Long): ResolvedPost?

    fun set(resolvedPost: ResolvedPost)

    fun delete(postId: Long)
}
