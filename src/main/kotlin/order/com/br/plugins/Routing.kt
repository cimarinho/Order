package order.com.br.plugins

import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import order.com.br.handler.orderRoutes
import order.com.br.model.OrderError

fun Application.configureRouting() {

    routing {
        orderRoutes()
    }

    install(StatusPages) {

        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }


        exception<OrderValidationException> { call, cause ->
            cause.error.forEach { println(it) }
            call.respond(status = HttpStatusCode.BadRequest, cause.error)
        }

        exception<OrderException> { call, cause ->
            val error = OrderError("err", cause.error)
            call.respond(status = HttpStatusCode.BadRequest, error)
        }

    }

}

class OrderException(val error: String) : RuntimeException()
class OrderValidationException(val error: List<OrderError>) : RuntimeException()