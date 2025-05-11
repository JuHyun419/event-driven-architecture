package jh

import jh.inspected.InspectedPost

interface PostElasticsearchPort {

    fun indexing(post: InspectedPost)

    fun delete(postId: Long)
}
