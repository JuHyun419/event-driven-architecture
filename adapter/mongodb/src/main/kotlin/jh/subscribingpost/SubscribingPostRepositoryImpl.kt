package jh.subscribingpost

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class SubscribingPostRepositoryImpl(
    private val mongoTemplate: MongoTemplate,
) : SubscribingPostCustomRepository {

    val logger = KotlinLogging.logger { }

    override fun findByFollowerUserIdWithPagination(
        followerUserId: Long,
        pageNumber: Int,
        pageSize: Int,
    ): MutableList<SubscribingPostDocument> {
        val query = Query()
            .addCriteria(Criteria.where("followerUserId").`is`(followerUserId))
            .with(
                PageRequest.of(
                    pageNumber,
                    pageSize,
                    Sort.by(Sort.Direction.DESC, "postCreatedAt")
                )
            )

        logger.info { "findByFollowerUserIdWithPagination Query: $query" }

        return mongoTemplate.find(
            query,
            SubscribingPostDocument::class.java,
            "subscribingInboxPosts"
        )
    }

    override fun deleteAllByPostId(postId: Long) {
        TODO("Not yet implemented")
    }
}
