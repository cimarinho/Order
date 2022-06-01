package order.com.br.service

import order.com.br.model.Order

interface OrderService {

    fun list(limit: Int, size: Int): MutableList<Order>

    fun get(orderId: String): Order

    fun save(order: Order): Order

    fun put(orderId: String, order: Order)

    fun delete(orderId: String)
}












