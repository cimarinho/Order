package order.com.br

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import order.com.br.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
