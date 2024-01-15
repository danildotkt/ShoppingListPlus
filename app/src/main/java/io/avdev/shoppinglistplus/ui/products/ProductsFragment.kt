package io.avdev.shoppinglistplus.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.avdev.domain.model.ShoppingList
import io.avdev.shoppinglistplus.adapter.ProductAdapter
import io.avdev.shoppinglistplus.databinding.FragmentProductsBinding
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment(var shoppingList: ShoppingList) : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private var _viewModel: ProductsViewModel? = null
    private lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var factory: ProductsViewModelFactory

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this, factory)[ProductsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        onClickDone()
    }

    private fun onClickDone() {
        binding.apply {
            etProduct.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val productName = etProduct.text.toString()
                    productAdapter.addProduct(productName)
                    etProduct.text = null
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun initRcView() {
        binding.apply {
            rcProducts.layoutManager = LinearLayoutManager(context)
            val adapter = viewModel.provideProductAdapter(this@ProductsFragment)
            adapter.setRcView(rcProducts)
            productAdapter = adapter
            rcProducts.adapter = productAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}