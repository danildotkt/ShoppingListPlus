package io.avdev.shoppinglistplus.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.domain.usecase.item.CreateItemUseCase
import io.avdev.shoppinglistplus.adapter.ItemAdapter
import io.avdev.shoppinglistplus.databinding.FragmentProductsBinding
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment(val shoppingList : ShoppingList) : Fragment() {
    private lateinit var binding : FragmentProductsBinding
    private lateinit var itemAdapter : ItemAdapter
    @Inject lateinit var createItemUseCase : CreateItemUseCase
    @Inject lateinit var factory : ProductsViewModel.Factory

    private val viewModel: ProductsViewModel by viewModels {
        ProductsViewModel.providesProductsViewModel(factory)
    }


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
                itemAdapter.addItem(productName)
                etProduct.text = null
                true
            } else {
                false
            }
        }
    }

    private fun initRcView() = with(binding){
        rcProducts.layoutManager = LinearLayoutManager(context)
        itemAdapter = viewModel.provideItemAdapter(this@ProductsFragment)
        rcProducts.adapter = itemAdapter
    }
}