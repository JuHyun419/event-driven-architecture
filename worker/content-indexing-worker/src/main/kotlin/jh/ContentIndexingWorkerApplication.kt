package jh

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContentIndexingWorkerApplication

fun main(args: Array<String>) {
    runApplication<ContentIndexingWorkerApplication>(*args)
}
