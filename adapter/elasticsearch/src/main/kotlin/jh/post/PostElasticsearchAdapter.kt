package jh.post

import jh.PostElasticsearchPort
import jh.inspected.InspectedPost
import jh.post.PostDocumentConverter.Companion.toDocument
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Component

@Component
class PostElasticsearchAdapter(
    private val postElasticsearchRepository: PostElasticsearchRepository,
    private val elasticsearchOperations: ElasticsearchOperations,
) : PostElasticsearchPort {

    override fun indexing(post: InspectedPost) {
        postElasticsearchRepository.save(toDocument(post))
    }

    override fun delete(postId: Long) {
        postElasticsearchRepository.deleteById(postId)
    }

    override fun searchPostIdsByKeyword(
        keyword: String,
        pageNumber: Int,
        pageSize: Int,
    ): List<Long> {
        // TODO: validation

        val query = buildQuery(keyword, pageSize, pageSize)
        val searchHits: SearchHits<PostDocument> = elasticsearchOperations.search(query, PostDocument::class.java)

        return searchHits.searchHits.stream().map { it.content.id }.toList()
    }

    private fun buildQuery(keyword: String, pageNumber: Int, pageSize: Int): Query {
        val criteria = Criteria("title").matches(keyword)
            .or(Criteria("content").matches(keyword))
            .or(Criteria("categoryName").`is`(keyword))
            .or(Criteria("tags").`is`(keyword))

        return CriteriaQuery(criteria).setPageable(PageRequest.of(pageNumber, pageSize))
    }
}
