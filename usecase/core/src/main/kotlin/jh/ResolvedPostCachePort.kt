package jh

import jh.post.model.ResolvedPost

interface ResolvedPostCachePort {

    fun get(postId: Long): ResolvedPost?

    fun multiGet(postIds: List<Long>): List<ResolvedPost>

    fun set(resolvedPost: ResolvedPost)

    fun delete(postId: Long)
}
