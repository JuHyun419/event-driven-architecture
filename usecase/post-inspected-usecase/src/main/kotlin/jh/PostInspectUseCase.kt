package jh

import jh.inspected.InspectedPost
import jh.post.model.Post

interface PostInspectUseCase {

    fun inspectAndGetIfValid(post: Post): InspectedPost?
}
