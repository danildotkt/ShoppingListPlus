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
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.list.AddListUseCase
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.FragmentCreateListBinding
import io.avdev.shoppinglistplus.ui.main.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateListFragment : Fragment() {
    private lateinit var binding: FragmentCreateListBinding
    @Inject lateinit var shoppingAdapter: ShoppingListAdapter
    @Inject lateinit var addListUseCase : AddListUseCase

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
        val shoppingList = ShoppingList(null, name, emptyList<ShoppingItem>().toMutableList())
        CoroutineScope(Dispatchers.IO).launch {
            addListUseCase.execute(shoppingList)
        }
        shoppingAdapter.addShoppingList(shoppingList)
        etListName.text = null
    }
    private fun initKeyboard() = with(binding){
        etListName.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etListName, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun moveToStartFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, MainFragment())
            .commit()
    }


}