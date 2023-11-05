package io.avdev.shoppinglistplus.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.avdev.shoppinglistplus.databinding.FragmentProductsBinding


class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        private var instance: ProductsFragment? = null

        fun getInstance(): ProductsFragment {
            if (instance == null) {
                instance = ProductsFragment()
            }
            return instance as ProductsFragment
        }
    }
}