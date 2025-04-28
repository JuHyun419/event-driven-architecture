package jh

import jh.inspected.InspectedPost
import jh.post.model.Post
import org.springframework.stereotype.Service

@Service
class PostInspectService(
    private val metadataPort: MetadataPort,
    private val autoInspectPort: PostAutoInspectPort,
) : PostInspectUseCase {

    override fun inspectAndGetIfValid(post: Post): InspectedPost? {
        val categoryName = metadataPort.getCategoryNameByCategoryId(post.categoryId)
        val inspectResult = autoInspectPort.inspect(post, categoryName)

        if (isNotValidInspect(inspectResult.status)) return null

        return InspectedPost.generate(post, categoryName, inspectResult.tags)
    }

    private fun isNotValidInspect(status: String): Boolean {
        return status != "GOOD"
    }
}
