package jh

import jh.post.model.Post
import org.springframework.stereotype.Service

@Service
class PostCreateService(
    private val postPort: PostPort
) : PostCreateUseCase {

    override fun create(request: PostCreateUseCase.CreateRequest): Post {
        return postPort.save(
            Post.generate(
                request.userId,
                request.title,
                request.content,
                request.categoryId
            )
        )
    }
}
