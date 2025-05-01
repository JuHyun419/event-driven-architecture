package jh

import jh.post.model.Post

interface SubscribingPostAddToInboxUseCase {

    fun saveSubscribingInboxPost(post: Post)
}
