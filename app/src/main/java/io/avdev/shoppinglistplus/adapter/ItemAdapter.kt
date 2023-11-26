package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingItem
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemProductBinding
import io.avdev.shoppinglistplus.service.ShoppingItemService
import io.avdev.shoppinglistplus.ui.products.ProductsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemAdapter(private val fragment : ProductsFragment,
                  private val itemService : ShoppingItemService) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<ShoppingItem>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fun validateListId() : Int{
                val id = fragment.shoppingList.id
                if(id != null){
                    return id
                }
                return 0
            }
            val listId = validateListId()
            itemList.addAll(itemService.selectItems(listId))
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    fun addItem(item: ShoppingItem) {
        itemList.add(item)
        val insertedIndex = itemList.indexOf(item)
        notifyItemInserted(insertedIndex)
    }


    inner class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)
        fun bind(item : ShoppingItem) = with(binding){
            textView.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shoppingItem = itemList[position]
        val viewHolder = holder as Holder
        viewHolder.bind(shoppingItem)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
}

