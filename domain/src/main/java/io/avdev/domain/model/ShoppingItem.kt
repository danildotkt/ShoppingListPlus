package io.avdev.domain.model

data class ShoppingItem(
    var id: Int? = null,
    var listId: Int,
    var name: String,
    var isSelected: Boolean = false
)