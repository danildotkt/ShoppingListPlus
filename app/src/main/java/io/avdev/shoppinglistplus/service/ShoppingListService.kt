package io.avdev.shoppinglistplus.service

import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.AddListUseCase
import io.avdev.domain.usecase.list.DeleteListUseCase
import io.avdev.domain.usecase.list.SelectListsUseCase
import io.avdev.domain.usecase.list.UpdateListUseCase

class ShoppingListService(
    private val addListUseCase: AddListUseCase,
    private val deleteListUseCase: DeleteListUseCase,
    private val selectListsUseCase: SelectListsUseCase,
    private val updateListUseCase: UpdateListUseCase)
{

    fun addList(list : ShoppingList) {
        addListUseCase.execute(list)
    }
    fun deleteList(list : ShoppingList) {
        deleteListUseCase.execute(list)
    }
    fun selectLists() : List<ShoppingList> {
        return selectListsUseCase.execute()
    }
    fun updateList(list : ShoppingList) {
        updateListUseCase.execute(list)
    }
}