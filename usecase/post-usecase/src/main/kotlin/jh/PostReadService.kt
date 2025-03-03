package jh

import jh.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostReadService: PostReadUseCase {

    override fun getById(id: Long): ResolvedPost {
        TODO("Not yet implemented")
    }
}
