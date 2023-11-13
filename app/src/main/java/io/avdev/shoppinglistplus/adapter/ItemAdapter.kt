package io.avdev.shoppinglistplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.avdev.domain.model.ShoppingItem
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.databinding.ItemProductBinding
import io.avdev.shoppinglistplus.service.ShoppingItemService


class ItemAdapter(private val itemService : ShoppingItemService) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<ShoppingItem>()
//    lateinit var shoppingList: ShoppingList

//    private fun init (){
//        CoroutineScope(Dispatchers.IO).launch {
//            val items = shoppingList.id?.let { itemService.selectItems(it) }
//            items?.let { itemList.addAll(it) }
//            withContext(Dispatchers.Main) {
//                notifyDataSetChanged()
//            }
//        }
//    }
    fun addItem(item: ShoppingItem) {
        itemList.add(item)
        val insertedIndex = itemList.indexOf(item)
        notifyItemInserted(insertedIndex)
    }


//    fun setList(shoppingList: ShoppingList) {
//        itemList = shoppingList.itemList.toMutableList()
//        this.shoppingList = shoppingList
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
//        init()
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shoppingItem = itemList[position]
        val viewHolder = holder as Holder
        viewHolder.bind(shoppingItem)
    }
    inner class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)
        fun bind(item : ShoppingItem) = with(binding){
            textView.text = item.name
        }
    }
}

