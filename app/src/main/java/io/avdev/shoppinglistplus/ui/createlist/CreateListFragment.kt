package io.avdev.shoppinglistplus.ui.createlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.FragmentCreateListBinding
import io.avdev.shoppinglistplus.service.ShoppingListService
import io.avdev.shoppinglistplus.utils.extensions.moveToStartFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateListFragment : Fragment() {
    private lateinit var binding: FragmentCreateListBinding
    @Inject lateinit var shoppingAdapter: ShoppingListAdapter
    @Inject lateinit var listService : ShoppingListService
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
    }
    private fun setButtons() {
        onClickDone()
        onClickCreate()
    }

    private fun onClickCreate() = with(binding){
        initKeyboard()
        buttonCreateList.setOnClickListener {
            addList()
            moveToStartFragment()
        }
    }
    private fun onClickDone() = with(binding) {
        etListName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addList()
                moveToStartFragment()
                true
            } else {
                false
            }
        }
    }
    private fun addList() = with(binding){
        var name = etListName.text.toString()
        if(name == "") {
            name = "Список покупок +"
        }
        saveShoppingList(name)
        etListName.text = null
    }
    private fun saveShoppingList(name : String) {
        val shoppingList = ShoppingList(id = generateId(), name = name)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listService.addList(shoppingList)
            } catch (e: Exception) {
                val coincidenceShoppingList = ShoppingList(id = generateId(), name = name)
                listService.addList(coincidenceShoppingList)
            }
        }
        shoppingAdapter.addShoppingList(shoppingList)
    }
    private fun generateId(): Int {
        return (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
    }

    private fun initKeyboard() = with(binding){
        etListName.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etListName, InputMethodManager.SHOW_IMPLICIT)
    }



}