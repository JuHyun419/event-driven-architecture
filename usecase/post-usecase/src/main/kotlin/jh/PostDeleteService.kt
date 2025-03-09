package jh

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostDeleteService(
    private val postPort: PostPort,
    private val messageProducePort: OriginalPostMessageProducePort
) : PostDeleteUseCase {

    @Transactional
    override fun delete(request: PostDeleteUseCase.DeleteRequest) {
        val post = postPort.findById(request.postId) ?: return

        post.delete()

        val deletedPost = postPort.save(post) // soft delete

        messageProducePort.sendDeleteMessage(deletedPost.id!!)
    }
}
