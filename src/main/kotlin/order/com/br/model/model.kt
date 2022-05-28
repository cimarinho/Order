package order.com.br.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Order(
    val id: String, @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    val purchase: LocalDateTime,
    val price: Double,
    var userName: String,
    var items: List<Items>
)

data class Items(val id: String, val product: String, val price: Double)