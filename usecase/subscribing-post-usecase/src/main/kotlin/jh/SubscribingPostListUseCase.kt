package jh

import jh.post.model.ResolvedPost

interface SubscribingPostListUseCase {

    // 구독자의 인박스에 있는 콘텐츠 목록 조회
    fun getSubscribingInboxPosts(request: Request): List<ResolvedPost>

    class Request(
        val pageNumber: Int,
        val followerUserId: Long,
    )
}
