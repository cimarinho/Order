package order.com.br.handler

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import order.com.br.model.Order
import order.com.br.model.OrderError
import order.com.br.model.validateUser
import order.com.br.plugins.OrderException
import order.com.br.plugins.OrderLocation
import order.com.br.plugins.OrderLocationId

import order.com.br.plugins.OrderValidationException
import order.com.br.service.impl.OrderServiceImpl

fun Routing.orderRoutes() {

    val orderService = OrderServiceImpl()

    get<OrderLocation>  {params ->
        call.respond(orderService.list(params.limit, params.size))
    }

    get<OrderLocationId>  { listing ->
        val get = orderService.get(listing.orderId)
        call.respond(HttpStatusCode.OK, get)
    }

    delete<OrderLocationId>  { listing ->
        call.respond(orderService.delete(listing.orderId))
    }


    put("/order/{orderId}") {
        val orderId = call.parameters.get("orderId") ?: throw OrderException("ID is required")
        val order = call.receive(Order::class)
        call.respond(orderService.put(orderId, order))
    }


    post("/order") {
        val order = call.receive(Order::class)
        val validationResult = validateUser(order)
        if (validationResult.errors.size > 0) {
            val orderErros: MutableList<OrderError> = ArrayList()
            validationResult.errors.forEach { orderErros.add(OrderError(it.dataPath, it.message)) }
            throw OrderValidationException(orderErros)
        }
        call.respond(orderService.save(order))
    }

}

