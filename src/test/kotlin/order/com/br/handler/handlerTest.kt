package order.com.br.handler

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import order.com.br.model.Items
import order.com.br.model.Order
import order.com.br.plugins.configureLocations
import order.com.br.plugins.configureRouting
import order.com.br.plugins.configureSerialization
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime


class HandlerTest {
    val mapper = jacksonObjectMapper()
    var order: Order? = null

    @Before
    fun prepareTest() {
        mapper.registerModule(JavaTimeModule())
        val item: MutableList<Items> = ArrayList()
        item.add(Items("23", "Monitor", 165.8))
        item.add(Items("153", "Teclado", 165.8))
        order = Order("123", LocalDateTime.now(), 63.5, "marinho", item)
    }

    @Test
    fun `get order by id`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }
        val response = client.get("/order/123")
        val order = mapper.readValue<Order>(response.bodyAsText())
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("123", order.id)
    }

    @Test
    fun `get orders`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }
        val response = client.get("/order?limit=2&size=10")
        print(response.bodyAsText())
        val order = mapper.readValue<List<Order>>(response.bodyAsText())
        print(order)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(1, order.size)
    }

    @Test
    fun `create order`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }
        val response = client.post("/order") {
            contentType(ContentType.Application.Json)
            setBody(mapper.writeValueAsString(order))
        }
        val orderResp = mapper.readValue<Order>(response.bodyAsText())
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(2, orderResp.items.size)
    }

    @Test
    fun `delete order`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }
        val response = client.delete("/order/123")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `update order`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }
        val response = client.put("/order/123") {
            contentType(ContentType.Application.Json)
            setBody(mapper.writeValueAsString(order))
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

}


