package jh

import jh.post.model.Post

interface SubscribingPostPort {

    // 콘텐츠가 발행되면 콘텐츠를 구독하고 있는 모두의 구독함에 넣는다.
    fun addPostToFollowerInboxes(post: Post, followerUserIds: List<Long>)

    // 특정 구독자의 구독목록 화면에서 구독하고 있는 유저가 생성한 콘텐츠 목록을 본다.
    fun getPostIdsByFollowerUserIdWithPagination(
        followerUserId: Long,
        pageNumber: Int,
        pageSize: Int,
    ): MutableList<Long>
}
