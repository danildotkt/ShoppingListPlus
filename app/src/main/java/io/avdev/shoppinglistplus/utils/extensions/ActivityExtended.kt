package io.avdev.shoppinglistplus.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import io.avdev.shoppinglistplus.ui.ShoppingListActivity


var AppCompatActivity.navigationCounter: Int
    get() = if (this::class == ShoppingListActivity::class) 0 else 1
    set(value) {}
