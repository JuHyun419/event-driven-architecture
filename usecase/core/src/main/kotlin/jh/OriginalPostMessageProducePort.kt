package jh

import jh.post.model.Post

interface OriginalPostMessageProducePort {

    fun sendCreateMessage(post: Post)

    fun sendUpdateMessage(post: Post)

    fun sendDeleteMessage(id: Long)
}
