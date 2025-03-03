package jh

import jh.post.model.Post
import org.springframework.stereotype.Service

@Service
class PostUpdateService: PostUpdateUseCase {

    override fun update(request: PostUpdateUseCase.UpdateRequest): Post {
        TODO("Not yet implemented")
    }
}
