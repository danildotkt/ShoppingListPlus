package io.avdev.shoppinglistplus.utils.listeners

import io.avdev.domain.model.ShoppingList

interface FragmentNavigation {
    fun setCreateListFragment()
    fun setProductsFragment(sList: ShoppingList)
    fun setRenameListFragment(sList: ShoppingList)
}