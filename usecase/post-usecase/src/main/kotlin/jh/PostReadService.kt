package jh

import jh.post.model.ResolvedPost
import org.springframework.stereotype.Service

@Service
class PostReadService(
    private val postResolvingHelpUseCase: PostResolvingHelperUseCase,
) : PostReadUseCase {

    override fun getById(id: Long): ResolvedPost {
        return postResolvingHelpUseCase.resolvePostById(id)
    }
}
