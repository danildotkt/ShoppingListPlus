package io.avdev.domain.model

data class ShoppingList(
    var id: Int? = null,
    var name: String,
    var itemList: List<ShoppingItem> = mutableListOf()
)