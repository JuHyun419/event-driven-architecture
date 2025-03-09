package jh

import jh.post.model.Post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCreateService(
    private val postPort: PostPort,
    private val messageProducePort: OriginalPostMessageProducePort
) : PostCreateUseCase {

    @Transactional
    override fun create(request: PostCreateUseCase.CreateRequest): Post {
        val post = postPort.save(
            Post.generate(
                request.userId,
                request.title,
                request.content,
                request.categoryId
            )
        )

        messageProducePort.sendCreateMessage(post)

        return post
    }
}
