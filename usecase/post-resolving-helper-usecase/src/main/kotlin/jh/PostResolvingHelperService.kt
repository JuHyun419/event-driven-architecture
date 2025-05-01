package jh

import jh.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostResolvingHelperService(
    private val metadataPort: MetadataPort,
    private val postPort: PostPort,
) : PostResolvingHelperUseCase {

    override fun resolvePostById(postId: Long): ResolvedPost {
        var resolvedPost: ResolvedPost? = null

        val post = postPort.findById(postId)

        if (post != null) {
            val userName = metadataPort.getUserNameByUserId(post.userId)
            val categoryName = metadataPort.getCategoryNameByCategoryId(post.categoryId)

            resolvedPost = ResolvedPost.generate(post, userName, categoryName)
        }

        return resolvedPost!!
    }

    override fun resolvePostsByIds(postIds: List<Long>): List<ResolvedPost> {
        return postIds.map { resolvePostById(it) }.toList()
    }
}
