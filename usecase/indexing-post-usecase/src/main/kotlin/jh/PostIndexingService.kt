package jh

import jh.inspected.InspectedPost
import org.springframework.stereotype.Service

@Service
class PostIndexingService(
    private val postElasticPort: PostElasticsearchPort,
) : PostIndexingUseCase {

    override fun save(post: InspectedPost) {
        postElasticPort.indexing(post)
    }

    override fun delete(postId: Long) {
        postElasticPort.delete(postId)
    }
}
