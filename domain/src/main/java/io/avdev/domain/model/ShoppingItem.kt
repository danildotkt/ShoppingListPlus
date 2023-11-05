package io.avdev.domain.model

data class ShoppingItem (
    var id : Int,
    var listId: Int,
    var name : String,
    var isSelected : Boolean = false
)