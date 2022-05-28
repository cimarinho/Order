package order.com.br.handler

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import order.com.br.model.Order
import order.com.br.plugins.OrderException
import order.com.br.repository.service.OrderService


fun Routing.orderRoutes(){

    val limitDefault = "1"
    val sizeDefault = "10"
    val orderService = OrderService()

    get("/order") {
        val limit = call.parameters.get("limit") ?: limitDefault
        val size = call.parameters.get("size")  ?: sizeDefault
        println("limit $limit e size $size")
        call.respond(orderService.list(limit.toInt(), size.toInt()))
    }

    get("/order/{orderId}") {
        val orderId = call.parameters.get("orderId") ?: throw OrderException("ID is required")
        call.respond(orderService.get(orderId))
    }

    put("/order/{orderId}") {
        val orderId = call.parameters.get("orderId") ?: throw OrderException("ID is required")
        val order = call.receive(Order::class)
        call.respond(orderService.put(orderId, order))
    }

    delete("/order/{orderId}") {
        val orderId = call.parameters.get("orderId") ?: throw OrderException("ID is required")
        call.respond(orderService.delete(orderId))
    }

    post("/order") {
        val order = call.receive(Order::class)
        call.respond(orderService.save(order))
    }

}