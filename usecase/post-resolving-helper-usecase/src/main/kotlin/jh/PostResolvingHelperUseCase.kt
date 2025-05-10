package jh

import jh.post.model.Post
import jh.post.model.ResolvedPost

interface PostResolvingHelperUseCase {

    fun resolvePostById(postId: Long): ResolvedPost

    fun resolvePostsByIds(postIds: List<Long>): List<ResolvedPost>

    fun resolvePostAndSave(post: Post)

    fun deleteResolvedPost(postId: Long)
}
