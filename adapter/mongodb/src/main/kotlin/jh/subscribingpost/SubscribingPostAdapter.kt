package jh.subscribingpost

import jh.SubscribingPostPort
import jh.post.model.Post
import org.springframework.stereotype.Component

@Component
class SubscribingPostAdapter(
    private val repository: SubscribingPostRepository
) : SubscribingPostPort {

    // 콘텐츠가 발행되면 콘텐츠를 구독하고 있는 모두의 구독함에 넣는다.
    override fun addPostToFollowerInboxes(
        post: Post,
        followerUserIds: List<Long>,
    ) {
        val documents = followerUserIds.map {
            SubscribingPostDocument.generate(post, it)
        }

        repository.saveAll(documents)
    }

    override fun removePostFromFollowerInboxes(postId: Long) {
        repository.deleteAllByPostId(postId)
    }

    // 특정 구독자의 구독목록 화면에서 구독하고 있는 유저가 생성한 콘텐츠 목록을 본다.
    override fun getPostIdsByFollowerUserIdWithPagination(
        followerUserId: Long,
        pageNumber: Int,
        pageSize: Int,
    ): MutableList<Long> {
        val documents = repository.findByFollowerUserIdWithPagination(
            followerUserId,
            pageNumber,
            pageSize
        )

        return documents.map { it.postId }.toMutableList()
    }
}
