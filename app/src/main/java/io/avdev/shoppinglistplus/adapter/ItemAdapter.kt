package io.avdev.shoppinglistplus.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.usecase.item.CreateItemUseCase
import io.avdev.domain.usecase.item.GetItemsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateItemSelectionUseCase
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemProductBinding
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemAdapter(getItemsByListIdUseCase: GetItemsByListIdUseCase,
    private val fragment: ProductsFragment,
    private val updateItemSelectionUseCase: UpdateItemSelectionUseCase,
    private val createItemUseCase: CreateItemUseCase
) : ListAdapter<ShoppingItem, ItemAdapter.Holder>(ItemDiffCallback()) {

    private val itemList = mutableListOf<ShoppingItem>()
    private lateinit var unselectedItems: List<ShoppingItem>
    private lateinit var selectedItems: List<ShoppingItem>

    init {
        val listId = fragment.shoppingList.id
        val flow = getItemsByListIdUseCase.execute(listId)
        CoroutineScope(Dispatchers.Default).launch {
            flow.collect { items ->
                withContext(Dispatchers.IO){
                    unselectedItems = (items.filter { !it.isSelected  })
                    selectedItems = items.filter { it.isSelected }
                    itemList.clear()
                    itemList.addAll(unselectedItems)
                    itemList.addAll(selectedItems)
                    withContext(Dispatchers.Main) {
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun addItem(productName: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val newItem = ShoppingItem(
                id = generateId(),
                listId = fragment.shoppingList.id,
                name = productName
            )
            itemList.add(newItem)
            createItemUseCase.execute(newItem)
            withContext(Dispatchers.Main) {
                unselectedItems.size + 1
            }
        }
    }

    private fun generateId(): Int {
        return (Int.MIN_VALUE..Int.MAX_VALUE).random()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)

        fun bind(item: ShoppingItem) {
            with(binding) {
                textView.text = item.name
                if (item.isSelected) {
                    textView.setTextColor(Color.GRAY)
                    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textView.setTextColor(ContextCompat.getColor(itemView.context, R.color.textColor))
                    textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                checkBox2.setOnCheckedChangeListener(null)
                checkBox2.isChecked = item.isSelected
                checkBox2.setOnCheckedChangeListener { b, isChecked ->
                    item.isSelected = isChecked
                    updateItemSelection(item, isChecked)
                }
            }
        }
    }
    private fun updateItemSelection(item: ShoppingItem, isSelected: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            updateItemSelectionUseCase.execute(item.id, isSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class ItemDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

    }
}

