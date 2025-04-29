package jh

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AutoInspectionWorkerApplication

fun main(args: Array<String>) {
    runApplication<AutoInspectionWorkerApplication>(*args)
}
