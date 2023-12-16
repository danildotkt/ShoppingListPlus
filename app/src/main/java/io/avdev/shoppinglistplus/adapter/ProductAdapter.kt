package io.avdev.shoppinglistplus.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.Product
import io.avdev.domain.usecase.item.CreateProductUseCase
import io.avdev.domain.usecase.item.GetProductsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateProductSelectionUseCase
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemProductBinding
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductAdapter(
    getProductsByListIdUseCase: GetProductsByListIdUseCase,
    private val fragment: ProductsFragment,
    private val updateProductSelectionUseCase: UpdateProductSelectionUseCase,
    private val createProductUseCase: CreateProductUseCase
) : RecyclerView.Adapter<ProductAdapter.Holder>() {

    private var unselectedProducts = mutableListOf<Product>()
    private var selectedProducts = mutableListOf<Product>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val listId = fragment.shoppingList.id
            val items = getProductsByListIdUseCase.execute(listId)
            unselectedProducts.addAll(items.filter { !it.isSelected }
                .filter { !unselectedProducts.contains(it) })
            selectedProducts.addAll(items.filter { it.isSelected }
                .filter { !selectedProducts.contains(it) })
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }


    fun addProduct(productName: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val newProduct = createProduct(productName)
            createProductUseCase.execute(newProduct)
            withContext(Dispatchers.Main) {
                unselectedProducts.add(newProduct)
                val index = unselectedProducts.indexOf(newProduct)
                notifyItemInserted(index)
            }
        }
    }


    private fun createProduct(productName: String): Product {
        val finalProductName = productName.ifBlank {
            randomProduct()
        }
        return Product(
            id = generateId(),
            listId = fragment.shoppingList.id,
            name = finalProductName
        )
    }

    private fun randomProduct(): String {
        val context = fragment.context
        val productNameList = context?.resources?.getStringArray(R.array.product_names)
        val productNameSize = productNameList?.size ?: 0
        val randomIndex = (0 until productNameSize).random()

        return productNameList?.get(randomIndex).toString()
    }

    private fun generateId(): Int {
        return (Int.MIN_VALUE..Int.MAX_VALUE).random()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)

        fun bind(item: Product) {
            setupText(item)
            setupCheckBox(item)
        }

        private fun setupText(item: Product) {
            with(binding) {
                textView.text = item.name
                if (item.isSelected) {
                    setProductTextSelected()
                } else {
                    setProductTextUnselected()
                }
            }
        }

        private fun setProductTextSelected() {
            with(binding) {
                textView.setTextColor(Color.GRAY)
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        private fun setProductTextUnselected() {
            with(binding) {
                textView.setTextColor(ContextCompat.getColor(itemView.context, R.color.textColor))
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        private fun setupCheckBox(item: Product) {
            with(binding) {
                checkBox2.setOnCheckedChangeListener(null)
                checkBox2.isChecked = item.isSelected
                checkBox2.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        handleCheckBoxChecked(item)
                    } else {
                        handleCheckBoxUnchecked(item)
                    }
                }
            }
        }

        private fun handleCheckBoxChecked(item: Product) {
            selectProduct(item)
            setProductTextSelected()
            updateProductSelection(item, true)
        }

        private fun handleCheckBoxUnchecked(item: Product) {
            unselectProduct(item)
            setProductTextUnselected()
            updateProductSelection(item, false)
        }
    }

    private fun selectProduct(item : Product) {
        val from = unselectedProducts.indexOf(item)
        val to = unselectedProducts.size - 1
        unselectedProducts.remove(item)
        selectedProducts.add(0, item)
        notifyItemMoved(from, to)
    }
    private fun unselectProduct(item : Product) {
        val from = unselectedProducts.size+ selectedProducts.indexOf(item)
        val to = unselectedProducts.size
        selectedProducts.remove(item)
        unselectedProducts.add(item)
        notifyItemMoved(from, to)
    }

    private fun updateProductSelection(product: Product, isSelected: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            updateProductSelectionUseCase.execute(product.id, isSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = if (position < unselectedProducts.size) {
            unselectedProducts[position]
        } else {
            selectedProducts[position - unselectedProducts.size]
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return unselectedProducts.size + selectedProducts.size
    }
}

