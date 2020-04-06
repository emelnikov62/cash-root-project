package ru.cash.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * Web application entry point.
 * @author emelnikov
 */
@SpringBootApplication
@Controller
class CashWebApp {

    /**
     * By default returns welcome page.
     */
    @GetMapping("/")
    fun layout(): String = "/${::layout.name}"
}

/**
 * Main function with custom [args] to start springboot application.
 */
@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<CashWebApp>(*args)
}