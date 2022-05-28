package order.com.br.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import order.com.br.handler.orderRoutes

fun Application.configureRouting() {

    routing {
        orderRoutes()
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }

        exception<OrderException> { call, cause ->
            val error = OrderError(cause.error)
            call.respond(status = HttpStatusCode.BadRequest, error)
        }

    }

}
data class OrderError(val message: String)
class OrderException(val error: String) : RuntimeException()