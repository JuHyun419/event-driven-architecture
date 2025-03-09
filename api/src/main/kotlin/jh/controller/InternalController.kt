package jh.controller

import jh.PostInspectUseCase
import jh.inspected.InspectedPost
import jh.post.model.Post
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
class InternalController(
    private val postInspectUseCase: PostInspectUseCase
) {

    @GetMapping
    fun inspectionTest(
        @RequestParam title: String,
        @RequestParam content: String,
        @RequestParam categoryId: Long,
    ): InspectedPost? {

        return postInspectUseCase.inspectAndGetIfValid(
            Post.generate(
                userId = 0L, // userId 는 검수 결과에 영향을 미치지 않으므로 아무 값이나 입력
                title = title,
                content = content,
                categoryId = categoryId
            )
        )
    }
}
