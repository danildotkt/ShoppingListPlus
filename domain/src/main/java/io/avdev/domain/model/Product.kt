package io.avdev.domain.model

data class Product(
    var id: Int,
    var listId: Int,
    var name: String,
    var isSelected: Boolean = false
)

