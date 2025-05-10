package jh

import jh.post.model.Post
import jh.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostResolvingHelperService(
    private val metadataPort: MetadataPort,
    private val postPort: PostPort,
    private val resolvedPostCachePort: ResolvedPostCachePort,
) : PostResolvingHelperUseCase {

    override fun resolvePostById(postId: Long): ResolvedPost {
        resolvedPostCachePort.get(postId)?.let { return it }

        val post = postPort.findById(postId)
            ?: throw IllegalArgumentException("Post id: $postId not found")

        return resolvedPost(post)
    }

    override fun resolvePostsByIds(postIds: List<Long>): List<ResolvedPost> {
        val resolvedPostCaches = resolvedPostCachePort.multiGet(postIds).toMutableList()
        val cachedPostIds = resolvedPostCaches.map { it.id }.toSet().toSet()

        // 캐시에 존재하지 않는 게시글 Ids
        val missingPostIds = postIds.filterNot { cachedPostIds.contains(it) }
        val missingResolvedPosts = postPort.findByIds(missingPostIds).map { resolvedPost(it) }

        resolvedPostCaches += missingResolvedPosts

        val resolvedPostMap = resolvedPostCaches.associateBy { it.id }

        // 요청한 postIds 의 게시글 순서대로 반환
        return postIds.mapNotNull { resolvedPostMap[it] }
    }

    override fun resolvePostAndSave(post: Post) {
        val resolvedPost = resolvedPost(post)

        resolvedPostCachePort.set(resolvedPost)
    }

    override fun deleteResolvedPost(postId: Long) {
        resolvedPostCachePort.delete(postId)
    }

    private fun resolvedPost(post: Post): ResolvedPost {
        val userName = metadataPort.getUserNameByUserId(post.userId)
        val categoryName = metadataPort.getCategoryNameByCategoryId(post.categoryId)

        return ResolvedPost
            .generate(post, userName, categoryName)
            .also { resolvedPostCachePort.set(it) }
    }
}
