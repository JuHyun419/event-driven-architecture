package jh.controller

import jh.dto.PostListResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/posts/list")
class PostListControllerV1 {

    @GetMapping("/inbox/{userId}")
    fun listSubscribingPosts(@PathVariable userId: Long): ResponseEntity<List<PostListResponse>> {
        return ResponseEntity.internalServerError().build()
    }

    @GetMapping("/search")
    fun search(@RequestParam("query") query: String): ResponseEntity<List<PostListResponse>> {
        return ResponseEntity.internalServerError().build()
    }
}
