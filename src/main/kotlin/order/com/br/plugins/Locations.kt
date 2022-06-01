package order.com.br.plugins

import io.ktor.server.application.*
import io.ktor.server.locations.*

fun Application.configureLocations() {
    install(Locations) {

    }
}

@Location("/order") data class OrderLocation(val limit: Int, val size: Int)

@Location("/order/{orderId}")
data class OrderLocationId(val orderId: String)