package jh.controller

import jh.SubscribingPostListUseCase
import jh.dto.PostListResponse
import jh.post.model.ResolvedPost
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/posts/list")
class PostListControllerV1(
    private val subscribingPostListUseCase: SubscribingPostListUseCase,
) {

    @GetMapping("/inbox/{userId}")
    fun listSubscribingPosts(
        @PathVariable userId: Long,
        @RequestParam(name = "page", defaultValue = "0", required = false) page: Int,
    ): ResponseEntity<List<PostListResponse>> {
        val subscribingInboxPosts = subscribingPostListUseCase.getSubscribingInboxPosts(
            SubscribingPostListUseCase.Request(page, userId)
        )

        return ResponseEntity.ok(subscribingInboxPosts.map { it -> toDto(it) })
    }

    @GetMapping("/search")
    fun search(@RequestParam("query") query: String): ResponseEntity<List<PostListResponse>> {
        return ResponseEntity.internalServerError().build()
    }

    private fun toDto(resolvedPost: ResolvedPost): PostListResponse {
        return PostListResponse(
            resolvedPost.id,
            resolvedPost.title,
            resolvedPost.userName,
            resolvedPost.createdAt,
        )
    }
}
