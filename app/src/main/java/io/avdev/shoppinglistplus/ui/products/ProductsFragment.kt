package io.avdev.shoppinglistplus.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.adapter.ItemAdapter
import io.avdev.shoppinglistplus.databinding.FragmentProductsBinding
import io.avdev.shoppinglistplus.service.ShoppingItemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment(val shoppingList : ShoppingList) : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    @Inject lateinit var itemService: ShoppingItemService
    private lateinit var itemAdapter : ItemAdapter

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
                val newItem = ShoppingItem(listId = shoppingList.id, name = productName)
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
            itemService.addItem(item)
        }
    }
    private fun initRcView() = with(binding){
        rcProducts.layoutManager = LinearLayoutManager(context)
        itemAdapter = ItemAdapter(this@ProductsFragment, itemService)
        rcProducts.adapter = itemAdapter
    }
}