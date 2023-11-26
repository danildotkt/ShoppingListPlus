package io.avdev.shoppinglistplus.utils.listeners

import io.avdev.domain.model.ShoppingList

interface FragmentNavigationClickListener {
    fun setCreateListFragment()
    fun setProductsFragment(sList : ShoppingList)
    fun setUpdateNameFragment(sList : ShoppingList)
}