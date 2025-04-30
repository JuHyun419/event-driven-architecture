package jh.subscribingpost

interface SubscribingPostCustomRepository {

    fun findByFollowerUserIdWithPagination(
        followerUserId: Long,
        pageNumber: Int,
        pageSize: Int,
    ): MutableList<SubscribingPostDocument>

    fun deleteAllByPostId(postId: Long)
}
