package jh

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

        val userName = metadataPort.getUserNameByUserId(post.userId)
        val categoryName = metadataPort.getCategoryNameByCategoryId(post.categoryId)

        return ResolvedPost
            .generate(post, userName, categoryName)
            .also { resolvedPostCachePort.set(it) }
    }

    override fun resolvePostsByIds(postIds: List<Long>): List<ResolvedPost> {
        return postIds.map { resolvePostById(it) }.toList()
    }
}
