package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingItem
import io.avdev.domain.usecase.item.CreateItemUseCase
import io.avdev.domain.usecase.item.GetItemsByListIdUseCase
import io.avdev.domain.usecase.item.UpdateItemSelectionUseCase
import io.avdev.shoppinglistplus.databinding.ItemProductBinding
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemAdapter(getItemsByListIdUseCase: GetItemsByListIdUseCase,
    private val fragment : ProductsFragment,
    private val updateItemSelectionUseCase: UpdateItemSelectionUseCase,
    private val createItemUseCase: CreateItemUseCase) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedItemList = mutableListOf<ShoppingItem>()
    private var unselectedItemList = mutableListOf<ShoppingItem>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val listId = fragment.shoppingList.id
            val list = getItemsByListIdUseCase.execute(listId)
            segregateItemList(list)
        }
    }

    private fun segregateItemList(list : List<ShoppingItem>) {
        unselectedItemList.addAll(list.filter { !it.isSelected }.sortedBy { it.name })
        selectedItemList.addAll((list.filter { it.isSelected }.sortedBy { it.name }))
    }



    fun addItem(productName : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val newItem = try {
                ShoppingItem(id = generateId(), listId = fragment.shoppingList.id, name = productName)
            } catch (_: Exception) {
                ShoppingItem(id = generateId(), listId = fragment.shoppingList.id, name = productName)
            }
            unselectedItemList.add(newItem)
            createItemUseCase.execute(newItem)
            withContext(Dispatchers.Main) {
                notifyItemInserted(unselectedItemList.indexOf(newItem))
            }
        }
    }

    private fun generateId(): Int {
        return (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
    }

    inner class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)
        fun bind(item : ShoppingItem) = with(binding) {
            textView.text = item.name
            checkBox2.setOnCheckedChangeListener(null)
            checkBox2.isChecked = item.isSelected
            checkBox2.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectItem(item)
                    updateItemSelection(item, true)
                }
                if (!isChecked){
                    unselectItem(item)
                    updateItemSelection(item, false)
                }
            }
        }
    }
    private fun selectItem(item : ShoppingItem) {
        unselectedItemList.remove(item)
        selectedItemList.add(item)
    }

    private fun unselectItem(item : ShoppingItem) {
        selectedItemList.remove(item)
        unselectedItemList.add(item)
    }

    private fun updateItemSelection(item : ShoppingItem, isSelected : Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            updateItemSelectionUseCase.execute(item.id, isSelected)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(io.avdev.shoppinglistplus.R.layout.item_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shoppingItem = if (position < unselectedItemList.size) {
            unselectedItemList[position]
        } else {
            selectedItemList[position - unselectedItemList.size]
        }
        val viewHolder = holder as Holder
        viewHolder.bind(shoppingItem)
    }
    override fun getItemCount(): Int {
        return unselectedItemList.size + selectedItemList.size
    }
}

