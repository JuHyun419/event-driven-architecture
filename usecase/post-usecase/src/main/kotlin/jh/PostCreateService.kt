package jh

import jh.post.model.Post
import org.springframework.stereotype.Service

@Service
class PostCreateService: PostCreateUseCase {

    override fun create(request: PostCreateUseCase.CreateRequest): Post {
        return Post.generate(
            request.userId,
            request.title,
            request.content,
            request.categoryId
        )
    }
}
