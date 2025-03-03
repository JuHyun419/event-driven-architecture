package jh.controller

import jh.PostCreateUseCase
import jh.PostDeleteUseCase
import jh.PostReadUseCase
import jh.PostUpdateUseCase
import jh.dto.PostCreateRequest
import jh.dto.PostDetailResponse
import jh.dto.PostResponse
import jh.dto.PostUpdateRequest
import jh.post.model.Post
import jh.post.model.ResolvedPost
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/posts")
class PostControllerV1(
    private val createUseCase: PostCreateUseCase,
    private val readUseCase: PostReadUseCase,
    private val updateUseCase: PostUpdateUseCase,
    private val deleteUseCase: PostDeleteUseCase,
) {

    @PostMapping
    fun create(
        @RequestBody request: PostCreateRequest
    ): ResponseEntity<PostResponse> {
        val post = createUseCase.create(
            PostCreateUseCase.CreateRequest(
                userId = request.userId,
                title = request.title,
                content = request.content,
                categoryId = request.categoryId,
            )
        )

        return ResponseEntity.ok().body(toDto(post))
    }

    @GetMapping("/{id}/detail")
    fun readDetail(@PathVariable id: Long): ResponseEntity<PostDetailResponse> {
        val resolvedPost = readUseCase.getById(id)

        return ResponseEntity.ok(toDto(resolvedPost))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: PostUpdateRequest
    ): ResponseEntity<PostResponse> {
        val post = updateUseCase.update(
            PostUpdateUseCase.UpdateRequest(
                postId = id,
                title = request.title,
                content = request.content,
                categoryId = request.categoryId,
            )
        )

        return ResponseEntity.ok().body(toDto(post))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        deleteUseCase.delete(
            PostDeleteUseCase.DeleteRequest(id)
        )

        return ResponseEntity.ok().build()
    }

    private fun toDto(post: Post): PostResponse {
        return PostResponse(
            id = post.id,
            title = post.title,
            content = post.content,
            userId = post.userId,
            categoryId = post.categoryId,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt,
            deletedAt = post.deletedAt,
        )
    }

    private fun toDto(resolvedPost: ResolvedPost): PostDetailResponse {
        return PostDetailResponse(
            id = resolvedPost.id,
            title = resolvedPost.title,
            content = resolvedPost.content,
            userName = resolvedPost.userName,
            categoryName = resolvedPost.categoryName,
            createdAt = resolvedPost.createdAt,
            updated = resolvedPost.updated
        )
    }
}
