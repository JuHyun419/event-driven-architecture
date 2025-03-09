package jh.controller

import jh.TestChatGptPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
class InternalController(
    private val testChatGptPort: TestChatGptPort
) {

    @GetMapping
    fun test(@RequestParam content: String): String {
        return testChatGptPort.test(content)
    }
}
