package order.com.br.handler


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import order.com.br.model.Items
import order.com.br.model.Order
import order.com.br.plugins.*
import org.junit.Before
import org.junit.Test


class HandlerTest {
    var order: Order? = null
    var gson: Gson? = null

    @Before
    fun prepareTest() {
        val builder = GsonBuilder()
        builder.setPrettyPrinting()
        gson = builder.create()
        val item: MutableList<Items> = ArrayList()
        item.add(Items("23", "Monitor", 165.8))
        item.add(Items("153", "Teclado", 165.8))
        order = Order("123", "2022-05-27 16:34", 63.5, "marinho", item)
    }

    @Test
    fun `get order by id`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }

        val response = client.get("/order/123")
        val order = gson?.fromJson(response.bodyAsText(), Order::class.java)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("123", order?.id)
    }


    @Test
    fun `get orders`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }
        val response = client.get("/order?limit=2&size=10")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `create order`() = testApplication {
        application {
            configureLocations()
            configureRouting()
            configureSerialization()
        }

        val jsonInString: String = gson!!.toJson(order)
        val response = client.post("/order") {
            contentType(ContentType.Application.Json)
            setBody(jsonInString)
        }
        val orderResp = gson!!.fromJson(response.bodyAsText(), Order::class.java)
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
            configureSerialization()
            configureLocations()
            configureRouting()
            configureSecurity()
            configureMonitoring()
        }
        val jsonInString: String = gson!!.toJson(order)
        val response = client.put("/order/123") {
            contentType(ContentType.Application.Json)
            setBody(jsonInString)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

}


