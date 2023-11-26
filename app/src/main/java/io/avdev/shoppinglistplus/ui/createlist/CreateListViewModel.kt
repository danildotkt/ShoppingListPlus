package io.avdev.shoppinglistplus.ui.createlist

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.service.ShoppingListService
import io.avdev.shoppinglistplus.ui.main.MainFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class CreateListViewModel @Inject constructor(private val listService: ShoppingListService) : ViewModel() {
//
//    fun addList(name: String) {
//        val shoppingList = ShoppingList(id = generateId(), name = name)
//        viewModelScope.launch {
//            try {
//                listService.addList(shoppingList)
//            } catch (e: Exception) {
//                val coincidenceShoppingList = ShoppingList(id = generateId(), name = name)
//                listService.addList(coincidenceShoppingList)
//            }
//        }
//    }
//
//    private fun generateId(): Int {
//        return (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
//    }
//
//    fun initKeyboard(context: Context, editText: EditText) {
//        editText.requestFocus()
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
//    }
//
//    fun moveToStartFragment(fragmentManager: FragmentManager) {
//        fragmentManager.beginTransaction()
//            .replace(R.id.fragmentLayout, MainFragment())
//            .commit()
//    }
//}