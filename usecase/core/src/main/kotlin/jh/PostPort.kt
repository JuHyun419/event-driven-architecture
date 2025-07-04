package jh

import jh.post.model.Post

interface PostPort {

    fun save(post: Post): Post

    fun findById(id: Long): Post?

    fun findByIds(ids: List<Long>): List<Post>
}
