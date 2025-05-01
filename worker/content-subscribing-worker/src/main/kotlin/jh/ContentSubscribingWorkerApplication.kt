package jh

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContentSubscribingWorkerApplication

fun main(args: Array<String>) {
    runApplication<ContentSubscribingWorkerApplication>(*args)
}
