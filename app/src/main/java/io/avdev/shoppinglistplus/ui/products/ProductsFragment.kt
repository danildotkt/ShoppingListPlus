package io.avdev.shoppinglistplus.ui.products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.item.AddItemUseCase
import io.avdev.shoppinglistplus.adapter.ItemAdapter
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import io.avdev.shoppinglistplus.databinding.FragmentProductsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    @Inject lateinit var itemAdapter: ItemAdapter
    @Inject lateinit var useCase: AddItemUseCase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        onClickDone()
    }
    private fun onClickDone() = with(binding) {
        etProduct.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val productName = etProduct.text.toString()
                val newItem = ShoppingItem(null, 10, productName, false)
                addProduct(newItem)
                etProduct.text = null
                true
            } else {
                false
            }
        }
    }
    private fun addProduct(item: ShoppingItem) {
        itemAdapter.addItem(item)
        saveItem(item)
    }
    private fun saveItem(item: ShoppingItem) {
        CoroutineScope(Dispatchers.IO).launch {
            if(item.listId == null) {
                throw NullPointerException("list id is null")
            }
            useCase.execute(item)
        }
    }
    private fun initRcView() = with(binding){
        rcProducts.layoutManager = LinearLayoutManager(context)
        rcProducts.adapter = itemAdapter
    }
}