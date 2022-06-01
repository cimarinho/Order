package order.com.br.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minItems
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum
import io.ktor.server.locations.*
import java.time.LocalDateTime

data class Order(

    val id: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    val purchase: LocalDateTime,
    val price: Double,
    var userName: String,
    var items: List<Items>

)
val validateUser = Validation<Order> {

    Order::id ifPresent {
        minLength(2)
        maxLength(20)
    }
    Order::price ifPresent {
        minimum(1)
    }

    Order::userName ifPresent {
        minLength(1)
    }

    Order::items {
        minItems(1)
    }

    Order::items onEach {
        Items::id ifPresent {
            minLength(2)
            maxLength(20)
        }
        Items::product {
            minLength(2)  hint "Please provide a name product"
        }
        // Email is optional but if it is set it must be valid
        Items::price ifPresent {
            minimum(18) hint "Please provide a price product"
        }
    }
}


data class Items(val id: String, val product: String, val price: Double)
data class OrderError(val field: String, val message: String)