package jh

import jh.post.model.Post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostUpdateService(
    private val postPort: PostPort,
    private val messageProducePort: OriginalPostMessageProducePort
) : PostUpdateUseCase {

    @Transactional
    override fun update(request: PostUpdateUseCase.UpdateRequest): Post? {
        val post = postPort.findById(request.postId) ?: return null

        post.update(request.title, request.content, request.categoryId)

        val savedPost = postPort.save(post)

        messageProducePort.sendCreateMessage(savedPost)

        return savedPost
    }
}
