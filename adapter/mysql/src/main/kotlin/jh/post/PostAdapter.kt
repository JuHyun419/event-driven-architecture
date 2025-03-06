package jh.post

import jh.PostPort
import jh.post.model.Post
import org.springframework.stereotype.Component

@Component
class PostAdapter(
    private val repository: PostJpaRepository
) : PostPort {

    override fun save(post: Post): Post {
        val postEntity = repository.save(PostEntityConverter.toEntity(post))

        return PostEntityConverter.toModel(postEntity)
    }

    override fun findById(id: Long): Post? {
        val postEntity = repository.findById(id).orElse(null) ?: return null

        return PostEntityConverter.toModel(postEntity)
    }
}
