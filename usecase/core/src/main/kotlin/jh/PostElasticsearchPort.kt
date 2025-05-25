package jh

import jh.inspected.InspectedPost

interface PostElasticsearchPort {

    fun indexing(post: InspectedPost)

    fun delete(postId: Long)

    fun searchPostIdsByKeyword(keyword: String, pageNumber: Int, pageSize: Int): List<Long>
}
