package order.com.br.handler

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import order.com.br.plugins.configureRouting
import order.com.br.repository.service.OrderService
import org.junit.Test
import kotlin.test.assertEquals

class HandlerTest {

    @Mock
    val orderService = OrderService()


    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }

        client.get("/order/1").apply {
            assertEquals(HttpStatusCode.OK, status)

        }
    }
}


