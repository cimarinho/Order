package order.com.br.repository

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.userRoutes(){
    get("/") {
        call.respondText("Hello World!")
    }

}