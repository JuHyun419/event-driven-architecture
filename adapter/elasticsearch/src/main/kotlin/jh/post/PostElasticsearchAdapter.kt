package jh.post

import jh.PostElasticsearchPort
import jh.inspected.InspectedPost
import jh.post.PostDocumentConverter.Companion.toDocument
import org.springframework.stereotype.Component

@Component
class PostElasticsearchAdapter(
    private val postElasticsearchRepository: PostElasticsearchRepository,
) : PostElasticsearchPort {

    override fun indexing(post: InspectedPost) {
        postElasticsearchRepository.save(toDocument(post))
    }

    override fun delete(postId: Long) {
        postElasticsearchRepository.deleteById(postId)
    }
}
