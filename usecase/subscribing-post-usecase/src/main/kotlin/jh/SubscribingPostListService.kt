package jh

import jh.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class SubscribingPostListService(
    private val subscribingPostPort: SubscribingPostPort,
    private val postResolvingHelperUseCase: PostResolvingHelperUseCase
) : SubscribingPostListUseCase {

    // 구독자의 인박스에 있는 콘텐츠 목록 조회
    override fun getSubscribingInboxPosts(request: SubscribingPostListUseCase.Request): List<ResolvedPost> {
        val subscribingPostIds = subscribingPostPort.getPostIdsByFollowerUserIdWithPagination(
            request.followerUserId,
            request.pageNumber,
            5
        )

        return postResolvingHelperUseCase.resolvePostsByIds(subscribingPostIds)
    }
}
